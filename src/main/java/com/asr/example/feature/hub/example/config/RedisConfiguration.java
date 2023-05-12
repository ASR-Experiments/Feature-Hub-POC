package com.asr.example.feature.hub.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host:127.0.0.1}")
    String redisHost;

    @Value("${redis.port:6379}")
    int redisPort;

    @Value("${redis.username:}")
    Optional<String> redisUsername;

    @Value("${redis.password:}")
    Optional<String> redisPassword;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        redisUsername
                .filter(StringUtils::hasText)
                .ifPresent(username -> {
                    serverConfig.setUsername(redisUsername.get());
                    serverConfig.setPassword(redisPassword.orElse(null));
                });
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }
}
