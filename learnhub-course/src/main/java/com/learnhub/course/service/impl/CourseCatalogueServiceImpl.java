package com.learnhub.course.service.impl;

import com.learnhub.api.client.exam.ExamClient;
import com.learnhub.api.dto.course.CatalogueDTO;
import com.learnhub.api.dto.exam.QuestionBizDTO;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.TreeDataUtils;
import com.learnhub.course.domain.po.CourseCatalogue;
import com.learnhub.course.mapper.CourseCatalogueMapper;
import com.learnhub.course.service.ICourseCatalogueService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 课程目录服务实现类
 *
 * @author lm
 * @since 2024-05-14 10:33:39
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class CourseCatalogueServiceImpl implements ICourseCatalogueService {

    private final CourseCatalogueMapper courseCatalogueMapper;
    private final ExamClient examClient;

    @Override
    public List<CatalogueDTO> queryCourseCatalogues(Long courseId, boolean withPractice) {
        //1.查询课程目录列表
        List<CourseCatalogue> courseCatalogues = courseCatalogueMapper.queryCatalogueList(courseId, withPractice);

        if (CollUtils.isEmpty(courseCatalogues)) {
            return null;
        }
        //3.查询课程目录对应题目数量
        Set<Long> ids = courseCatalogues.stream().map(CourseCatalogue::getId).collect(Collectors.toSet());
        List<QuestionBizDTO> questionBizDTOS = examClient.queryQuestionIdsByBizIds(ids);
        //4.转化目录id和题目id、分数对应关系
        Map<Long, Long> cataIdAndNumMap =
                CollUtils.isEmpty(questionBizDTOS)
                        ? new HashMap<>() :
                        questionBizDTOS
                                .stream()
                                .collect(Collectors.groupingBy(QuestionBizDTO::getBizId, Collectors.counting()));
        // 5.组织树结构并返回
        return TreeDataUtils.parseToTree(courseCatalogues, CatalogueDTO.class, (courseCatalogue, cataVO) -> {
            cataVO.setMediaName(courseCatalogue.getVideoName());
            cataVO.setIndex(courseCatalogue.getCIndex());
            cataVO.setSubjectNum(cataIdAndNumMap.getOrDefault(courseCatalogue.getId(), 0L).intValue());
        }, new CourseCatalogDataWrapper());
    }

    //课程目录树形转化模型
    private static class CourseCatalogDataWrapper implements TreeDataUtils.DataProcessor<CatalogueDTO, CourseCatalogue> {
        @Override
        public Object getParentKey(CourseCatalogue courseCatalogue) {
            return courseCatalogue.getParentCatalogueId();
        }

        @Override
        public Object getKey(CourseCatalogue courseCatalogue) {
            return courseCatalogue.getId();
        }

        @Override
        public Object getRootKey() {
            return 0L;
        }

        @Override
        public List<CatalogueDTO> getChild(CatalogueDTO catalogueDTO) {
            return catalogueDTO.getSections();
        }

        @Override
        public void setChild(CatalogueDTO parent, List<CatalogueDTO> child) {
            parent.setSections(child);
        }
    }
}
