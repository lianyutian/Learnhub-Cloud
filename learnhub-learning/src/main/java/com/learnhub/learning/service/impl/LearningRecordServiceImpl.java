package com.learnhub.learning.service.impl;

import com.learnhub.api.client.course.CourseClient;
import com.learnhub.api.dto.learning.LearningLessonDTO;
import com.learnhub.api.dto.learning.LearningRecordDTO;
import com.learnhub.common.exceptions.BizIllegalException;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.BeanUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.api.dto.course.CourseFullInfoDTO;
import com.learnhub.learning.domain.dto.LearningRecordFormDTO;
import com.learnhub.learning.domain.po.LearningLesson;
import com.learnhub.learning.domain.po.LearningRecord;
import com.learnhub.learning.enums.SectionType;
import com.learnhub.learning.mapper.LearningLessonMapper;
import com.learnhub.learning.mapper.LearningRecordMapper;
import com.learnhub.learning.service.ILearningLessonService;
import com.learnhub.learning.service.ILearningRecordService;
import com.learnhub.learning.utils.LearningRecordDelayTaskHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学习记录服务实现类
 *
 * @author lm
 * @since 2024-05-13 16:30:06
 * @version 1.0
 */
@Service
@AllArgsConstructor
@Slf4j
public class LearningRecordServiceImpl implements ILearningRecordService {

    private final ILearningLessonService learningLessonService;
    private final LearningRecordMapper learningRecordMapper;
    private final LearningLessonMapper learningLessonMapper;
    private final CourseClient courseClient;
    private final LearningRecordDelayTaskHandler taskHandler;

    @Override
    public LearningLessonDTO queryLearningRecordByCourseId(Long courseId) {
        // 1.获取登录用户
        Long userId = UserContext.getUserId();
        // 2.查询课表
        LearningLesson lesson = learningLessonService.queryByUserAndCourseId(userId, courseId);
        // 3.查询学习记录
        // select * from xx where lesson_id = #{lessonId}
        List<LearningRecord> records = learningRecordMapper.queryLearningRecordByLessonId(lesson.getId());
        // 4.封装结果
        LearningLessonDTO learningLessonDTO = new LearningLessonDTO();
        learningLessonDTO.setId(lesson.getId());
        learningLessonDTO.setLatestSectionId(lesson.getLatestSectionId());
        learningLessonDTO.setRecords(BeanUtils.copyList(records, LearningRecordDTO.class));
        return learningLessonDTO;
    }

    @Override
    @Transactional(rollbackFor = DbException.class)
    public void saveLearningRecord(LearningRecordFormDTO recordDTO) {
        // 1.获取登录用户
        Long userId = UserContext.getUserId();
        // 2.处理学习记录
        boolean finished;
        if (recordDTO.getSectionType() == SectionType.VIDEO) {
            // 2.1.处理视频
            finished = handleVideoRecord(userId, recordDTO);
        } else {
            // 2.2.处理考试
            finished = handleExamRecord(userId, recordDTO);
        }
        if (!finished) {
            // 没有新学完的小节，无需更新课表中的学习进度
            return;
        }

        // 3.处理课表数据
        handleLearningLessonsChanges(recordDTO);
    }

    @Override
    @Transactional(rollbackFor = DbException.class)
    public void updateLearningRecord(LearningRecord record) {
        learningRecordMapper.updateLearningRecordWithRecord(record);
    }

    private boolean handleVideoRecord(Long userId, LearningRecordFormDTO recordDTO) {
        // 1.查询旧的学习记录
        LearningRecord old = queryOldRecord(recordDTO.getLessonId(), recordDTO.getSectionId());
        // 2.判断是否存在
        if (old == null) {
            // 3.不存在，则新增
            // 3.1.转换PO
            LearningRecord record = BeanUtils.copyBean(recordDTO, LearningRecord.class);
            // 3.2.填充数据
            record.setUserId(userId);
            // 3.3.写入数据库
            boolean success = learningRecordMapper.saveLearningRecord(record);
            if (!success) {
                throw new DbException("新增学习记录失败！");
            }
            return false;
        }
        // 4.存在，则更新
        // 4.1.判断是否是第一次完成
        boolean finished = !old.getFinished() && recordDTO.getMoment() * 2 >= recordDTO.getDuration();

        if (!finished) {
            LearningRecord record = new LearningRecord();
            record.setLessonId(recordDTO.getLessonId());
            record.setSectionId(recordDTO.getSectionId());
            record.setMoment(recordDTO.getMoment());
            record.setId(old.getId());
            record.setFinished(old.getFinished());
            taskHandler.addLearningRecordTask(record);
            return false;
        }

        // 4.2.更新数据
        boolean success = learningRecordMapper.updateLearningRecord(recordDTO, old.getId());
        if (!success) {
            throw new DbException("更新学习记录失败！");
        }

        // 4.3.清理缓存
        taskHandler.cleanRecordCache(recordDTO.getLessonId(), recordDTO.getSectionId());
        return true;
    }

    private LearningRecord queryOldRecord(Long lessonId, Long sectionId) {
        // 1.查询缓存
        LearningRecord record = taskHandler.readRecordCache(lessonId, sectionId);
        // 2.如果命中，直接返回
        if (record != null) {
            return record;
        }
        // 3.未命中，查询数据库
        record = learningRecordMapper.queryLearningRecordByLessonIdAndSectionId(lessonId, sectionId);
        // 4.写入缓存
        taskHandler.writeRecordCache(record);
        return record;
    }

    private boolean handleExamRecord(Long userId, LearningRecordFormDTO recordDTO) {
        // 1.转换DTO为PO
        LearningRecord record = BeanUtils.copyBean(recordDTO, LearningRecord.class);
        // 2.填充数据
        record.setUserId(userId);
        record.setFinished(true);
        record.setFinishTime(recordDTO.getCommitTime());
        // 3.写入数据库
        boolean success = learningRecordMapper.saveLearningRecord(record);
        if (!success) {
            throw new DbException("新增考试记录失败！");
        }
        return true;
    }

    private void handleLearningLessonsChanges(LearningRecordFormDTO recordDTO) {
        // 1.查询课表
        LearningLesson lesson = learningLessonMapper.queryLearningLessonById(recordDTO.getLessonId());
        if (lesson == null) {
            throw new BizIllegalException("课程不存在，无法更新数据！");
        }

        // 2.如果有新完成的小节，则需要查询课程数据
        CourseFullInfoDTO cInfo = courseClient.getCourseInfoById(lesson.getCourseId(), false, false);
        if (cInfo == null) {
            throw new BizIllegalException("课程不存在，无法更新数据！");
        }

        // 3.比较课程是否全部学完：已学习小节 >= 课程总小节
        boolean allLearned = lesson.getLearnedSections() + 1 >= cInfo.getSectionNum();

        // 4.更新课表
        learningLessonMapper.updateLearningLesson(lesson.getLearnedSections(), allLearned, lesson.getId());
    }
}
