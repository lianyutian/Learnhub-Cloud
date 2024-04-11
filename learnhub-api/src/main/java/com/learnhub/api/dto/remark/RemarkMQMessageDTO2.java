package com.learnhub.api.dto.remark;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 17:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class RemarkMQMessageDTO2  {
    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 点赞叔
     */
    private Integer likes;
}
