package com.learnhub.api.dto.remark;

import com.learnhub.common.autoconfigure.mq.BaseMessage;
import lombok.Data;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 17:28
 */
@Data
public class RemarkMessageDTO extends BaseMessage {
    private Long bizId;
    private int likes;
}
