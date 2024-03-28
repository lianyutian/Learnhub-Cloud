package com.learnhub.common.exceptions;

/**
 * 通用错误信息
 *
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2023/10/25 22:05]
 */
public enum CommonError {
    /**
     * 未知错误
     */
    UNKOWN_ERROR("执行过程异常，请重试。"),

    /**
     * 非法参数
     */
    PARAMS_ERROR("非法参数"),

    /**
     * 对象为空
     */
    OBJECT_NULL("对象为空"),

    /**
     * 查询结果为空
     */
    QUERY_NULL("查询结果为空"),

    /**
     * 请求参数为空
     */
    REQUEST_NULL("请求参数为空");

    private final String errMessage;

    private String code = "";

    public String getErrMessage() {
        return errMessage;
    }

    public String getCode() {
        return code;
    }

    CommonError(String errMessage) {
        this.errMessage = errMessage;
    }

    CommonError(String code, String errMessage) {
        this.code = code;
        this.errMessage = errMessage;
    }
}
