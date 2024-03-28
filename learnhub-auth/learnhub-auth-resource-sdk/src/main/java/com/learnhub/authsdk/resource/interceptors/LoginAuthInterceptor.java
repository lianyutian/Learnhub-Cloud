package com.learnhub.authsdk.resource.interceptors;

import com.learnhub.common.utils.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 20:28]
 */
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.尝试获取用户信息
        Long userId = UserContext.getUser();
        // 2.判断是否登录
        if (userId == null) {
            response.setStatus(401);
            response.sendError(401, "未登录用户无法访问！");
            // 2.3.未登录，直接拦截
            return false;
        }
        // 3.登录则放行
        return true;
    }
}
