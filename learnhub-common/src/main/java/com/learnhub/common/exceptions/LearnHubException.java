package com.learnhub.common.exceptions;

/**
 * 项目异常类
 *
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2023/10/25 22:07]
 */
public class LearnHubException extends RuntimeException {
    private String code;
    private String errMessage;

    public LearnHubException() {
        super();
    }

    public LearnHubException(String errMessage) {
        super(errMessage);
        this.errMessage = errMessage;
    }

    public LearnHubException(String code, String errMessage) {
        super(errMessage);
        this.code = code;
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new LearnHubException(commonError.getCode(), commonError.getErrMessage());
    }

    public static void cast(String errMessage) {
        throw new LearnHubException(errMessage);
    }

    public static void cast(String code, String errMessage) {
        throw new LearnHubException(code, errMessage);
    }
}
