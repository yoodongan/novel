package com.brad.novel.cache;

import com.brad.novel.preference.dto.BestPreferenceDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CacheTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void t1() {
        String key = "prefer";
        BestPreferenceDto bestPreferenceDto = new BestPreferenceDto(1L, 3L);
        BestPreferenceDto bestPreferenceDto2 = new BestPreferenceDto(2L, 5L);
        List<BestPreferenceDto> dtos = new ArrayList<>();
        dtos.add(bestPreferenceDto);
        dtos.add(bestPreferenceDto2);

        final ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(String.valueOf(bestPreferenceDto.getNovelId()), bestPreferenceDto);

        final BestPreferenceDto result = (BestPreferenceDto) valueOperations.get(String.valueOf(bestPreferenceDto.getNovelId()));
        assertThat(result).isNotNull();

        System.out.println(result.getNovelId());
        System.out.println(result.getLikeNumber());
    }

}
