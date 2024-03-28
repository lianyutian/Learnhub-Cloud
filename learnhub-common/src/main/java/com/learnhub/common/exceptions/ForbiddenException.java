package com.learnhub.common.exceptions;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/22 16:11
 */
public class ForbiddenException  extends CommonException {
    private final int status = 403;

    public ForbiddenException(String message) {
        super(403, message);
    }

    public ForbiddenException(int code, String message) {
        super(code, message);
    }

    public ForbiddenException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
