package com.brad.novel.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public Boolean lock(final Long id) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(String.valueOf(id), "lock", Duration.ofMillis(3000));
    }

    public Boolean unlock(final Long id) {
        return redisTemplate.delete(String.valueOf(id));
    }
}
