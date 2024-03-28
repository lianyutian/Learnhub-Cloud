package com.learnhub.common.exceptions;

/**
 * 业务不合法异常
 *
 * @author liming
 * @version 1.0
 * @since 2024/3/15 16:34
 */
public class BizIllegalException extends CommonException {
    public BizIllegalException(String message) {
        super(message);
    }

    public BizIllegalException(int code, String message) {
        super(code, message);
    }

    public BizIllegalException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
