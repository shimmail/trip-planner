package org.example.gateway.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.common.result.Result;
import org.example.common.utils.JwtUtil;
import org.example.gateway.config.AuthProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
@Slf4j
public class RateLimitGatewayFilter implements GlobalFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final StringRedisTemplate redisTemplate;
    private final AuthProperties authProperties;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            // 1. 获取Request对象，以访问请求的相关信息
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().toString();
            // 限流逻辑
            if (!checkRateLimit(request)) {
                log.info("限流触发，拒绝请求：{}", path);
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                String errorMessage = Result.error("请求过于频繁，请稍后再试").toJson();
                return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMessage.getBytes(StandardCharsets.UTF_8))));
            }

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
            System.out.println("userId = " + userId); // 打印用户ID供调试
            String userInfo = userId.toString();
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(builder -> builder.header("user-info", userInfo)) // 将用户信息保存到请求头
                    .build();
            // 6. 放行请求
            return chain.filter(exchange);

        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            // 设置未授权状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            // 设置返回的 JSON 错误信息
            String errorMessage = Result.error(e.getMessage()).toJson();
            // 将错误信息写入响应体
            return response.writeWith(Mono.just(response.bufferFactory().wrap(errorMessage.getBytes())));
        }


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


    private boolean checkRateLimit(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (ip == null) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
        }
        String path = request.getPath().toString();
        String key = ip + ":" + path;
        String count = redisTemplate.opsForValue().get(key);
        if (count == null) {
            redisTemplate.opsForValue().set(key, "1", 10, TimeUnit.SECONDS); // 10秒内最多访问一次
            return true;
        } else if (Integer.parseInt(count) < 5) { // 限制每10秒最多访问5次
            redisTemplate.opsForValue().increment(key);
            return true;
        }
        return false;
    }
}
