/*
package org.example.gateway.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.utils.JwtUtil;
import org.example.gateway.config.AuthProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private final JwtUtil jwtUtil;
    private final AuthProperties authProperties;
    @Override
    public GatewayFilter apply(Object config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                try {
                    // 1. 获取Request对象，以访问请求的相关信息
                    ServerHttpRequest request = exchange.getRequest();
                    String path = request.getPath().toString();
                    // 2. 判断请求路径是否在白名单中
                    if (isIgnoreUrl(path)){
                        log.info("直接放行");
                        return chain.filter(exchange);
                    }
                    // 3. 获取请求头中的token
                    String token = null;
                    List<String> headers = request.getHeaders().get("Authorization");


                    if (!(headers == null || headers.isEmpty())) {
                        token = headers.get(0);
                    }

                    // 4. 使用jwtTool校验并解析token
                    Long userId = null;
                    userId = jwtUtil.getId(token);

                    log.info("请求路径: {}", path);
                    log.info("请求头: {}", request.getHeaders());
                    log.info("Token: {}", token);
                    log.info("id: {}", userId);
                    String userInfo = userId.toString();
                    ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(builder -> builder.header("user-info", userInfo)) // 将用户信息保存到请求头
                            .build();
                    // 放行
                    return chain.filter(exchange);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
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
}*/
