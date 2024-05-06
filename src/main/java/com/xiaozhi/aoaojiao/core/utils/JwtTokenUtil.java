package com.xiaozhi.aoaojiao.core.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.jwt.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author xiaozhi
 *
 * JWT工具类
 */
@Data
@Component
@ConfigurationProperties("jwt")
public class JwtTokenUtil {

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 过期时间，单位：秒
     */
    private long expireTime;

    /**
     * 自定义标识
     */
    private String header;

    /**
     * 签发者
     */
    private String iss;

    /**
     * 接收者
     */
    private String sub;

    public String createToken(String username) {
        /*
            iss（全称为 issuer），指明 JWT 是由谁签发的
            sub（全称为 subject），指明 JWT 的主题（也可理解为面向用户的类型）
            aud（全称为 audience），指明 JWT 希望谁签收
            exp（全称为 expiration time），指明 JWT 的过期时间，过期时间需大于签发时间
            nbf（全称为 not before time），指明 JWT 在哪个时间点生效
            iat（全称为 issued at time），指明 JWT 的签发时间
            jti（全称为 JWT ID），指明 JWT 唯一 ID，用于避免重放攻击
         */
        var payloads = new HashMap<String, Object>();
        payloads.put("iss", this.iss);
        payloads.put("sub", this.sub);
        payloads.put("uuid", IdUtil.fastSimpleUUID());
        payloads.put("aud", username);
        return JWTUtil.createToken(payloads, secret.getBytes());
    }

    public String createToken() {
        return createToken(null);
    }

    public boolean verifyToken(String token) {
        return JWTUtil.verify(token, secret.getBytes());
    }

    public String getToken(HttpServletRequest request) {
        return request.getHeader(this.header);
    }

    public static void main(String[] args) {
        var payloads = new HashMap<String, Object>();
        payloads.put("username", "xiaozhi");
        var jwtToken = JWTUtil.createToken(payloads, "xxxxx32423".getBytes());

    }
}
