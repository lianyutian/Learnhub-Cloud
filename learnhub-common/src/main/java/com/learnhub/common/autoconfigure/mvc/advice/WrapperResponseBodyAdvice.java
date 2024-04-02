package com.learnhub.common.autoconfigure.mvc.advice;

import com.learnhub.common.constants.Constant;
import com.learnhub.common.domain.Result;
import com.learnhub.common.utils.WebUtils;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/1 22:04]
 */
@RestControllerAdvice
public class WrapperResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType() != Result.class && WebUtils.isGatewayRequest();
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if ("/v3/api-docs".equals(request.getURI().getPath())) {
            return body;
        }
        if (body == null) {
            return Result.ok().requestId(MDC.get(Constant.REQUEST_ID_HEADER));
        }
        if (body instanceof Result) {
            return body;
        }
        return Result.ok(body).requestId(MDC.get(Constant.REQUEST_ID_HEADER));
    }
}
