/*
package org.example.gateway.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

@Bean
public RequestInterceptor userInfoRequestInterceptor(){
    // 定义一个 Feign 的 RequestInterceptor Bean，用于在请求发出前执行自定义拦截操作
    return new RequestInterceptor() {
        @Override
        public void apply(RequestTemplate template) {
            // 获取当前登录用户的ID
            Long userId = UserContext.getUser();

            // 检查用户ID是否为 null，如果为 null 则表示用户未登录或无法获取用户信息
            if(userId == null) {
                // 如果用户ID为空，不做任何处理，直接返回，跳过拦截器逻辑
                return;
            }

            // 如果用户ID不为空，将用户ID作为请求头添加到 Feign 请求中
            // "user-info" 是请求头的键，下游微服务可以从该请求头中获取用户ID
            template.header("user-info", userId.toString());
        }
    };
}
*/
