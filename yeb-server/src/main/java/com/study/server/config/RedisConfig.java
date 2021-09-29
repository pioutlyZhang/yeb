package com.study.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis的配置类
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //设置String类型的redis Key序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置String类型的redis Value序列化器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //设置Hash类型的redis Key序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //设置Hash类型的redis Value序列化器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
