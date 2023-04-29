package com.brad.novel.preference.service;

import com.brad.novel.global.redis.RedisCacheKey;
import com.brad.novel.preference.entity.Preference;
import com.brad.novel.preference.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"preference"})
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    @Transactional(readOnly = true)
    public List<Preference> getPreferenceNovel(Long memberId, Integer likeNumber) {
        List<Preference> preferences = preferenceRepository.findByMemberIdAndLikeNumberOverTwo(memberId, likeNumber);
        return preferences;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = RedisCacheKey.PREFERENCE_LIST)
    public List<Object[]> getBestPreferencePerHour() {
         return preferenceRepository.findByBestReferencePerHour();
    }

}
