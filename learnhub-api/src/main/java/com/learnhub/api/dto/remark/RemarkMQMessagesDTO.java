package com.learnhub.api.dto.remark;

import com.learnhub.common.autoconfigure.mq.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/8 17:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RemarkMQMessagesDTO extends BaseMessage {
    List<RemarkMQMessageDTO2> remarkMQMessageDTOS;
}
