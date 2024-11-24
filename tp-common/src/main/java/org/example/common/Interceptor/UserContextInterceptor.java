package org.example.common.Interceptor;


import org.example.common.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserContextInterceptor implements HandlerInterceptor {

    // 通过 preHandle 在请求到达处理方法前获取用户信息
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("user-info");

        if (userId != null) {
            // 将用户ID存储到 ThreadLocal 中
            UserContext.setUser(Long.parseLong(userId));
        }

        // 继续请求流程
        return true;
    }

    // 请求处理完毕后清除 ThreadLocal 中的用户信息
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
