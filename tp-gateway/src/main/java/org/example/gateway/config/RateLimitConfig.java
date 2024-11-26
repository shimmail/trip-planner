/*
package org.example.gateway.config;

import com.alibaba.nacos.api.remote.request.Request;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.gateway.utils.IPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@EnableAutoConfiguration
public class RateLimitConfig {

    private final StringRedisTemplate redisTemplate;
    private final IPUtils ipUtils;
    public RateLimitConfig(StringRedisTemplate redisTemplate, IPUtils ipUtils) {
        this.redisTemplate = redisTemplate;
        this.ipUtils = ipUtils;
    }

    // 定义切点，匹配所有带有 @AccessLimit 注解的方法
    @Pointcut("@annotation(AccessLimit)")
    public void rateLimitPointcut() {}

    // 在目标方法执行之前执行限流逻辑
    @Before("rateLimitPointcut() && @annotation(rateLimit)")
    public void checkRateLimit(JoinPoint joinPoint, AccessLimit rateLimit) {
        // 获取 HttpServletRequest 参数
        HttpServletRequest request = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
                break;
            }
        }
        if (request != null) {
            // 获取客户端 IP 地址
            String ip = ipUtils.getClientIpAddress(request);
            String key = ip + ":" + rateLimit.toString();
            String count = redisTemplate.opsForValue().get(key);

            if (count == null) {
                redisTemplate.opsForValue().set(key, "1", rateLimit.seconds(), TimeUnit.SECONDS);
            } else if (Integer.parseInt(count) < rateLimit.maxCount()) {
                redisTemplate.opsForValue().increment(key);
            } else {
                // 超过限流次数，抛出异常
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "请求过于频繁，请稍后再试");
            }
        }
    }
}
*/
