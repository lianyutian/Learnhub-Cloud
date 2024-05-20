package com.learnhub.api.dto.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程详细信息
 *
 * @author lm
 * @since 2024-05-13 17:29:08
 * @version 1.0
 */
@Data
@Schema(description = "课程详细信息，包含课程、目录、教师", name = "CourseFullInfoDTO")
public class CourseFullInfoDTO {
    @Schema(description = "课程id", name = "id")
    private Long id;
    @Schema(description = "课程名称", name = "name")
    private String name;
    @Schema(description = "封面链接", name = "coverUrl")
    private String coverUrl;
    @Schema(description = "价格", name = "price")
    private Integer price;
    @Schema(description = "一级课程分类id", name = "firstCateId")
    private Long firstCateId;
    @Schema(description = "二级课程分类id", name = "secondCateId")
    private Long secondCateId;
    @Schema(description = "三级课程分类id", name = "thirdCateId")
    private Long thirdCateId;
    @Schema(description = "课程总节数", name = "sectionNum")
    private Integer sectionNum;
    @Schema(description = "课程购买有效期结束时间", name = "purchaseEndTime")
    private LocalDateTime purchaseEndTime;
    @Schema(description = "课程学习有效期", name = "validDuration")
    private Integer validDuration;
    @Schema(description = "课程章信息", name = "chapters")
    private List<CatalogueDTO> chapters;
    @Schema(description = "老师列表", name = "teacherIds")
    private List<Long> teacherIds;
    @JsonIgnore
    public List<Long> getCategoryIds(){
        return List.of(firstCateId, secondCateId, thirdCateId);
    }
}
