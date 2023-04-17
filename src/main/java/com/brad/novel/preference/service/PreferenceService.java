package com.brad.novel.preference.service;

import com.brad.novel.common.response.DataResponse;
import com.brad.novel.global.redis.RedisCacheKey;
import com.brad.novel.preference.dto.BestPreferenceDto;
import com.brad.novel.preference.dto.ResultPreferenceDto;
import com.brad.novel.preference.entity.Preference;
import com.brad.novel.preference.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"preference"})
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public DataResponse getPreferenceNovel(Long memberId, Integer likeNumber) {
        List<Preference> preferences = preferenceRepository.findByMemberIdAndLikeNumberOverTwo(memberId, likeNumber);
        List<ResultPreferenceDto> results = preferences.stream()
                .map(p -> ResultPreferenceDto.of(p.getNovel(), p.getRecentCh()))
                .collect(Collectors.toList());
        return DataResponse.success(results);
    }

    @Cacheable(value = RedisCacheKey.PREFERENCE_LIST)
    public DataResponse getBestPreferencePerHour() {
        List<Object[]> best = preferenceRepository.findByBestReferencePerHour();
        List<BestPreferenceDto> bestPreferenceDtos = new ArrayList<>();
        for (Object[] objects : best) {
            bestPreferenceDtos.add(new BestPreferenceDto((Long) objects[0], (Long) objects[1]));
        }
        return DataResponse.success(bestPreferenceDtos);
    }



}
