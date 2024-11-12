package org.example.gateway.filters;

import javafx.application.Application;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.gateway.config.AuthProperties;
import org.example.gateway.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
@Slf4j
public class Filter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;

    private final AuthProperties authProperties;




    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取Request对象，以访问请求的相关信息
        ServerHttpRequest request = exchange.getRequest();

        // 2. 判断请求路径是否在白名单中
        if (isIgnoreUrl(request.getPath().toString())) {
            log.info("直接放行");
            return chain.filter(exchange);
        }

        // 3. 获取请求头中的token（假设token放在"authorization"头部）
        String token = null;
        List<String> headers = request.getHeaders().get("authorization");
        if (!(headers == null || headers.isEmpty())) { // 如果请求头包含authorization字段
            token = headers.get(0);
        }

        // 4. 使用jwtTool校验并解析token
        Long userId = null;
        try {
            userId = jwtUtil.getId(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("userId = " + userId); // 打印用户ID供调试
        String userInfo = userId.toString();
        ServerWebExchange modifiedExchange = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo)) // 将 "user-info" 头设置为用户ID字符串
                .build();

        // 6. 放行请求
        return chain.filter(exchange);
    }

    private boolean isIgnoreUrl(String path) {
        List<String> ignoreUrls = authProperties.getIgnoreUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (path.matches(ignoreUrl.replace("**", ".*"))) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int getOrder() {
        return 0;
    }

}
