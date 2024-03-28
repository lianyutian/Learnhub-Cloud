package com.learnhub.common.exceptions;

/**
 * 数据库异常
 *
 * @author liming
 * @version 1.0
 * @since 2024/3/15 16:21
 */
public class DbException extends CommonException {
    public DbException(String message) {
        super(message);
    }

    public DbException(int code, String message) {
        super(code, message);
    }

    public DbException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
