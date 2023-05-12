package com.asr.example.feature.hub.example.config.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Getter
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProperties {

    Map<String, List<String>> agentsPhases;

    final RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    void postConstruct() {
        agentsPhases = Map.of(
                "test2@yopmail.com", List.of("PHASE2"),
                "test1@yopmail.com", List.of("PHASE1")
        );

        SetOperations<String, String> ops = redisTemplate.opsForSet();
        agentsPhases.forEach((key, value) -> ops.add(key, value.toArray(String[]::new)));
    }
}
