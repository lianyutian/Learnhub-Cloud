package com.learnhub.api.dto.remark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 17:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class LikedTimesDTO {
    private Long bizId;
    private Integer likedTimes;
}
