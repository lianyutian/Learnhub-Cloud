package com.learnhub.learning.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/15 10:51
 */
@Data
@Schema(description = "签到结果", name = "SignResultVO")
public class SignResultVO {
    @Schema(description = "连续签到天数", name = "signDays")
    private Integer signDays;
    @Schema(description = "签到得分", name = "signPoints")
    private Integer signPoints;
    @Schema(description = "连续签到奖励积分，连续签到超过7天以上才有奖励", name = "rewardPoints")
    private Integer rewardPoints;

    @JsonIgnore
    public int totalPoints(){
        return signPoints + rewardPoints;
    }
}
