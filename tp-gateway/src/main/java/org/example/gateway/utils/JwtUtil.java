package org.example.gateway.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "sss";

    public static final long TOKEN_EXPIRE_TIME = 7200 * 1000;     // token过期时间
    // 校验 Token
    public boolean validateToken(String token) throws Exception {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new Exception("token无效", e);
        }
    }

    // 生成 Token 示例
    public String generateToken(long id) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 校验 Token 并从中提取 ID
    public Long getId(String token) throws Exception {
        try {
            // 解析 token 并提取 Claims
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            // 获取 subject 字段中的 id（假设 id 是作为 subject 存储的）
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            throw new Exception("token无效", e);
        }
    }
}
