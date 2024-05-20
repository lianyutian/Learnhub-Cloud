package com.learnhub.api.client.exam;

import com.learnhub.api.dto.exam.QuestionBizDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * ExamClient
 *
 * @author lm
 * @since 2024-05-14 10:53:27
 * @version 1.0
 */
@FeignClient("exam-service")
public interface ExamClient {

    @GetMapping("/question-biz/biz/list")
    List<QuestionBizDTO> queryQuestionIdsByBizIds(@RequestParam("ids") Iterable<Long> bizIds);
}
