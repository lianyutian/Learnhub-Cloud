package com.learnhub.course.domain.dto;

import com.learnhub.common.exceptions.BadRequestException;
import com.learnhub.common.utils.DateUtils;
import com.learnhub.common.validate.Checker;
import com.learnhub.course.constants.CourseErrorInfo;
import com.learnhub.course.utils.CourseSaveBaseGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程基本信息
 *
 * @author lm
 * @since 2024-05-15 17:43:03
 * @version 1.0
 */
@Data
@Schema(description = "课程基本信息保存", name = "CourseBaseInfoSaveDTO")
public class CourseBaseInfoSaveDTO implements Checker<CourseBaseInfoSaveDTO> {
    @Schema(description = "课程id，新课程该值不能传，老课程必填", name = "id")
    private Long id;
    @Schema(description = "课程名称", name = "name")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_NAME_NULL)
    private String name;
    @Schema(description = "三级课程分类id", name = "thirdCateId")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_CATEGORY_NULL)
    private Long thirdCateId;
    @Schema(description = "封面链接url", name = "coverUrl")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_COVER_URL_NULL, groups = CourseSaveBaseGroup.class)
    private String coverUrl;
    @Schema(description = "是否是免费", name = "free")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_FREE_NULL)
    private Boolean free;
    @Schema(description = "课程价格", name = "price")
    @Min(value = 0, message = CourseErrorInfo.Msg.COURSE_SAVE_PRICE_NEGATIVE)
    private Integer price;
    @Schema(description = "购买周期结束时间", name = "purchaseEndTime")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_PURCHASE_TIME_NULL)
    private LocalDateTime purchaseEndTime;
    @Schema(description = "课程介绍", name = "introduce")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_INTRODUCE_NULL, groups = CourseSaveBaseGroup.class)
    private String introduce;
    @Schema(description = "使用人群", name = "usePeople")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_USE_PEOPLE_NULL, groups = CourseSaveBaseGroup.class)
    private String usePeople;
    @Schema(description = "详情", name = "detail")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_DETAIL_NULL, groups = CourseSaveBaseGroup.class)
    private String detail;
    @Schema(description = "学习周期，0或不传表示没有期限，其他表示月数", name = "validDuration")
    @NotNull(message = CourseErrorInfo.Msg.COURSE_SAVE_DURATION_NULL)
    private Integer validDuration;

    @Override
    public void check() {
        if (!free) { //非免费
            if (price == null) { //付费课程未设置价格
                throw new BadRequestException(CourseErrorInfo.Msg.COURSE_SAVE_PRICE_NULL);
            }
            if (price <= 0) { //付费课程设置价格小于0
                throw new BadRequestException(CourseErrorInfo.Msg.COURSE_SAVE_PRICE_NEGATIVE);
            }
        } else { //免费
            if (price != null && price > 0) { //免费课程设置了价格
                throw new BadRequestException(CourseErrorInfo.Msg.COURSE_SAVE_PRICE_FREE);
            }
        }
        if (purchaseEndTime.isBefore(DateUtils.now())) {
            throw new BadRequestException(CourseErrorInfo.Msg.COURSE_SAVE_PURCHASE_ILLEGAL);
        }
    }
}
