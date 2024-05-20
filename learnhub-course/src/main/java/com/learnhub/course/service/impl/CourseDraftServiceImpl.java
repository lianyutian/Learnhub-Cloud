package com.learnhub.course.service.impl;

import com.github.yitter.idgen.YitIdHelper;
import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.NumberUtils;
import com.learnhub.common.utils.ViolationUtils;
import com.learnhub.course.constants.CourseConstants;
import com.learnhub.course.constants.CourseErrorInfo;
import com.learnhub.course.constants.CourseStatus;
import com.learnhub.course.domain.dto.CourseBaseInfoSaveDTO;
import com.learnhub.course.domain.po.Course;
import com.learnhub.course.domain.po.CourseContentDraft;
import com.learnhub.course.domain.po.CourseDraft;
import com.learnhub.course.domain.vo.CourseSaveVO;
import com.learnhub.course.domain.vo.NameExistVO;
import com.learnhub.course.mapper.CourseContentDraftMapper;
import com.learnhub.course.mapper.CourseDraftMapper;
import com.learnhub.course.mapper.CourseMapper;
import com.learnhub.course.service.ICategoryService;
import com.learnhub.course.service.ICourseDraftService;
import jakarta.validation.ValidatorFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 草稿课程服务实现类
 *
 * @author lm
 * @since 2024-05-15 17:20:13
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CourseDraftServiceImpl implements ICourseDraftService {

    private final CourseDraftMapper courseDraftMapper;
    private final ValidatorFactory validatorFactory;
    private final ICategoryService categoryService;
    private final CourseMapper courseMapper;
    private final CourseContentDraftMapper courseContentDraftMapper;

    @Override
    public NameExistVO checkName(String name, Long id) {
        //1.草稿课程同名课程数量
        int num = courseDraftMapper.countCourseByNameAndId(name, id);
        return new NameExistVO(num > 0);
    }

    @Override
    @Transactional(rollbackFor = {DbException.class, Exception.class})
    public CourseSaveVO saveCourseBaseInfo(CourseBaseInfoSaveDTO courseBaseInfoSaveDTO) {

        List<Long> categoryIdList = null;
        Course course = null;
        //1.数据校验
        if (courseBaseInfoSaveDTO.getId() == null) {
            //1.1新增数据调起数据校验器
            ViolationUtils.process(validatorFactory.getValidator().validate(courseBaseInfoSaveDTO));
            //1.1.2.校验课程分类
            categoryIdList = categoryService.checkCategory(courseBaseInfoSaveDTO.getThirdCateId());
        } else {
            //1.2.未上架课程校验
            course = courseMapper.queryCourseById(courseBaseInfoSaveDTO.getId());
            if (course == null) {
                //1.2.1.未上架课程校验请求参数
                ViolationUtils.process(validatorFactory.getValidator().validate(courseBaseInfoSaveDTO));
                //1.2.2.同名课程判空
                checkSameCourse(courseBaseInfoSaveDTO.getId(), courseBaseInfoSaveDTO.getName());
                //1.2.3.校验课程分类
                categoryIdList = categoryService.checkCategory(courseBaseInfoSaveDTO.getThirdCateId());
            }
        }

        CourseDraft courseDraft = new CourseDraft();
        //2.数据封装
        //2.1.content数据封装 课程介绍、课程细节、适用人群
        CourseContentDraft courseContentDraft = new CourseContentDraft();
        courseContentDraft.setCourseIntroduce(courseBaseInfoSaveDTO.getIntroduce());
        courseContentDraft.setCourseDetail(courseBaseInfoSaveDTO.getDetail());
        courseContentDraft.setUsePeople(courseBaseInfoSaveDTO.getUsePeople());
        //2.2.课程封面和课程下架时间
        courseDraft.setCoverUrl(courseBaseInfoSaveDTO.getCoverUrl());
        courseDraft.setPurchaseEndTime(courseBaseInfoSaveDTO.getPurchaseEndTime());
        //2.3.未上架数据封装，已上架课程不能修改字段
        if (course == null) {
            //2.3.1.课程价格
            courseDraft.setPrice(NumberUtils.null2Zero(courseBaseInfoSaveDTO.getPrice()));
            //2.3.2.课程有效期
            courseDraft.setValidDuration(courseBaseInfoSaveDTO.getValidDuration());
            //2.3.3.课程状态
            courseDraft.setStatus(CourseStatus.NO_UP_SHELF.getStatus());
            //2.3.4.一级课程分类id
            courseDraft.setFirstCateId(categoryIdList.get(0));
            //2.3.5.二级课程分类id
            courseDraft.setSecondCateId(categoryIdList.get(1));
            //2.3.6.三级课程分类id
            courseDraft.setThirdCateId(categoryIdList.get(2));
            //2.3.7.售卖模式
            courseDraft.setFree(courseBaseInfoSaveDTO.getFree() ? 1 : 0);
            //2.3.8.课程名称
            courseDraft.setName(courseBaseInfoSaveDTO.getName());
        }

        //3.操作
        if (courseBaseInfoSaveDTO.getId() == null) {
            //3.1.新增课程草稿
            //3.1.1.新生成课程id
            Long id = YitIdHelper.nextId();
            //3.1.2.设置课程id
            courseContentDraft.setId(id);
            courseDraft.setId(id);
            //3.1.3.设置课程编辑进度
            courseDraft.setStep(CourseConstants.CourseStep.BASE_INFO);
            //3.1.4.插入课程草稿
            courseDraftMapper.saveCourseDraft(courseDraft);
            //3.1.5.插入课程草稿内容
            courseContentDraftMapper.saveCourseContentDraft(courseContentDraft);
        } else {
            //3.2.编辑课程草稿
            //3.2.1.设置课程id
            courseContentDraft.setId(courseBaseInfoSaveDTO.getId());
            courseDraft.setId(courseBaseInfoSaveDTO.getId());
            //3.2.2.更新课程草稿
            courseDraftMapper.updateCourseDraft(courseDraft);
            //3.2.3.更新课程草稿内容
            courseContentDraftMapper.updateCourseContentDraft(courseContentDraft);
        }
        //4.返回课程新增dto
        return CourseSaveVO
                .builder()
                .id(courseDraft.getId())
                .build();
    }

    private void checkSameCourse(Long id, String name) {
        //1.查询正式环境是否有同名课程
        int countSameNameNum = courseMapper.countCourseByNameAndId(name, null);
        //1.1.同名课程数据判0
        if (countSameNameNum > 0) { //名称已经存在，提交时做双重校验
            throw new BadRequestException(CourseErrorInfo.Msg.COURSE_SAVE_NAME_EXISTS);
        }
        //2.查询草稿环境是否有同名课程
        countSameNameNum = courseDraftMapper.countCourseByNameAndId(name, id);
        //2.1.同名课程数据判0
        if (countSameNameNum > 0) {
            throw new BadRequestException(CourseErrorInfo.Msg.COURSE_SAVE_NAME_EXISTS);
        }
    }
}
