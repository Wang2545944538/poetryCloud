package com.yunmo.back.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class TestJwtUtils {

    // 与 JwtUtils 中 @Value("${jwt.secret-key}") 保持一致，必须是合法 Base64 字符串
    @Value("${jwt.secret-key}") //用application.properties中的配置 赋值
    public static String JWT_KEY = "abcd";

    /**
     * 构造用于测试的 SecretKey，与正式环境一致
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JwtUtils.JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 生成测试用 JWT
     *
     * @param subject 内容（如 {"user_id": 1}）
     * @param ttlMillis 有效时间（毫秒）
     * @return JWT字符串
     */
    public static String createJWT(String subject, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(nowMillis + ttlMillis))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    /**
     * 快捷构造 token，1小时有效期
     */
    public static String createTokenForUserId(int userId) {
        String subject = "{\"user_id\":" + userId + "}";
        return createJWT(subject, 3600_000); // 一小时有效
    }
}
