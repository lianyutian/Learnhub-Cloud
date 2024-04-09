package com.learnhub.api.dto.remark;

import com.learnhub.common.autoconfigure.mq.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 17:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RemarkMQMessageDTO extends BaseMessage implements Serializable {
    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 点赞叔
     */
    private Integer likes;
}
