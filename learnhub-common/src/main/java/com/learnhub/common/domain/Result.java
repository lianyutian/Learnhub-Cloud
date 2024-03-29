package com.learnhub.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static com.learnhub.common.constants.ErrorInfo.Code.FAILED;
import static com.learnhub.common.constants.ErrorInfo.Code.SUCCESS;
import static com.learnhub.common.constants.ErrorInfo.Msg.OK;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:47]
 */
@Schema(name = "通用响应结果")
@Data
public class Result<T> {
    @Schema(name = "业务状态码，200-成功，其它-失败")
    private int code;
    @Schema(name = "响应消息", example = "OK")
    private String msg;
    @Schema(name = "响应数据")
    private T data;
    @Schema(name = "请求id", example = "1af123c11412e")
    private String requestId;

    public static Result<Void> ok() {
        return new Result<Void>(SUCCESS, OK, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(SUCCESS, OK, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(FAILED, msg, null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;

    }

    public boolean success() {
        return code == SUCCESS;
    }

    public Result<T> requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }
}
