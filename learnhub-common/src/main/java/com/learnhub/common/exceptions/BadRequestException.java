package com.learnhub.common.exceptions;

import lombok.Getter;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/15 16:16
 */
@Getter
public class BadRequestException extends CommonException {
    private final int status = 400;

    public BadRequestException(String message) {
        super(400, message);
    }

    public BadRequestException(int code, String message) {
        super(code, message);
    }

    public BadRequestException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
