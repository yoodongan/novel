package com.brad.novel.preference.service;

import com.brad.novel.common.response.DataResponse;
import com.brad.novel.global.redis.RedisCacheKey;
import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.dto.NovelResponseDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.preference.dto.BestPreferenceDto;
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

    public DataResponse getPreferenceNovel(Long memberId, Long likeNumber) {
        List<Preference> preferences = preferenceRepository.findByMemberIdAndLikeNumberGreaterThan(memberId, likeNumber);
        List<Novel> novels = preferences.stream().map(Preference::getNovel).collect(Collectors.toList());
        List<NovelResponseDto> collect = novels.stream()
                .map((novel) -> NovelResponseDto.of(novel))
                .collect(Collectors.toList());
        return DataResponse.success(collect);
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
