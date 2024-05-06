package com.xiaozhi.aoaojiao.core.constants;

/**
 * @author xiaozhi
 *
 * Redis 的常量
 */
public class RedisConstants {

    public final static String SPLIT = ":";

    public final static String LOGIN_TOKEN = "login:token";

    public final static String LOGIN_CODE = "login:code";

    public static String getLoginTokenKey(String token) {
        return LOGIN_TOKEN + SPLIT + token;
    }

    /**
     * 获取验证码的 key
     * @param uid   唯一id，不同的登录方式的验证码对应的 uid 不同
     * @return  返回 key
     */
    public static String getLoginCodeKey(String uid) {
        return LOGIN_CODE + SPLIT + uid;
    }
}
