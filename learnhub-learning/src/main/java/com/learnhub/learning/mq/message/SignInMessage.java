package com.learnhub.learning.mq.message;

import com.learnhub.common.autoconfigure.mq.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户积分
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/15 13:49
 */
@Data
@AllArgsConstructor(staticName = "of")
public class SignInMessage extends BaseMessage {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 积分
     */
    private Integer points;
}
