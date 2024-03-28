package com.learnhub.common.exceptions;

import lombok.Getter;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:51]
 */
@Getter
public class UnauthorizedException extends CommonException {
    private final int status = 401;

    public UnauthorizedException(String message) {
        super(401, message);
    }

    public UnauthorizedException(int code, String message) {
        super(code, message);
    }

    public UnauthorizedException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
