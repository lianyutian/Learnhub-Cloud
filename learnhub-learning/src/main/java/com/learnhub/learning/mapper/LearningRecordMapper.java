package com.learnhub.learning.mapper;

import com.learnhub.learning.domain.dto.LearningRecordFormDTO;
import com.learnhub.learning.domain.po.LearningRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * LearningRecordMapper
 *
 * @author lm
 * @since 2024-05-13 16:42:51
 * @version 1.0
 */
@Mapper
public interface LearningRecordMapper {

    /**
     * 根据lessonId查询学习记录
     *
     * @param id lessonId
     * @return learningRecordList
     */
    List<LearningRecord> queryLearningRecordByLessonId(Long id);

    /**
     * 根据lessonId和sectionId查询学习记录
     *
     * @param lessonId  课表id
     * @param sectionId 小节id
     * @return learningRecord
     */
    LearningRecord queryLearningRecordByLessonIdAndSectionId(Long lessonId, Long sectionId);

    /**
     * 保存学习记录
     *
     * @param record learningRecord
     * @return boolean
     */
    boolean saveLearningRecord(LearningRecord record);


    /**
     * 更新学习记录
     *
     * @param recordDTO 学习记录
     * @param id        学习记录id
     * @return boolean
     */
    boolean updateLearningRecord(LearningRecordFormDTO recordDTO, Long id);

    /**
     * 更新学习记录
     *
     * @param record 学习记录
     */
    void updateLearningRecordWithRecord(LearningRecord record);
}
