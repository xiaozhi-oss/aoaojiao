package com.xiaozhi.aoaojiao.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author xiaozhi
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        var redisTemplate = new RedisTemplate<String, Object>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 创建 jackson 序列化对象
        var jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        var stringRedisSerializer = new StringRedisSerializer();
        // 设置 key 的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // 设置 hash key 的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置 value 的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // 设置 hash value 的序列化方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 设置支持事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
