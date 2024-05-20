package com.learnhub.api.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 课程目录DTO
 *
 * @author lm
 * @since 2024-05-13 17:33:10
 * @version 1.0
 */
@Data
@Schema(description = "课程目录", name = "CatalogueDTO")
public class CatalogueDTO {
    @Schema(description = "章、节、练习id", name = "id")
    private Long id;
    @Schema(description = "序号", name = "index")
    private Integer index;
    @Schema(description = "章节练习名称", name = "name")
    private String name;
    @Schema(description = "课程总时长,单位秒", name = "mediaDuration")
    private Integer mediaDuration;
    @Schema(description = "是否支持免费试看", name = "trailer")
    private Boolean trailer;
    @Schema(description = "媒资名称", name = "mediaName")
    private String mediaName;
    @Schema(description = "媒资id", name = "mediaId")
    private Long mediaId;
    @Schema(description = "目录类型1：章，2：节，3：测试", name = "type")
    private Integer type;
    @Schema(description = "题目数量", name = "subjectNum")
    private Integer subjectNum;
    @Schema(description = "题目总分", name = "totalScore")
    private Integer totalScore;
    @Schema(description = "是否可以修改,默认不能修改", name = "canUpdate")
    private Boolean canUpdate = false;
    @Schema(description = "该章的所有小节和练习", name = "sections")
    private List<CatalogueDTO> sections;
}
