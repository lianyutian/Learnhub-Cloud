package com.learnhub.common.exceptions;

import lombok.Getter;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/15 16:16
 */
@Getter
public class CommonException extends RuntimeException {
    private final int code;

    public CommonException(String message) {
        super(message);
        this.code = 0;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        this.code = 0;
    }

    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getStatus(){
        return 500;
    };
}
