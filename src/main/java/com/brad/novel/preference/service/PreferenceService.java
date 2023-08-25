package com.brad.novel.preference.service;

import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.repository.NovelRepository;
import com.brad.novel.preference.dto.response.PreferenceNovelsResponseDto;
import com.brad.novel.preference.entity.Preference;
import com.brad.novel.preference.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.brad.novel.common.error.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"preference"})
@Transactional
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final NovelRepository novelRepository;


    /* 선호 작품 등록하기 */
    public Long registerPreferenceNovel(Member member, Long novelId) {
        if(preferenceRepository.existsByMemberAndNovelId(member, novelId)) {
            throw new NovelServiceException(DUPLICATED_PREFERENCE_NOVEL);
        }

        Novel novel = novelRepository.findById(novelId)
                .orElseThrow(() -> new NovelServiceException(NOT_FOUND_NOVEL));
        Preference preference = Preference.createPreferenceNovel(member, novel);
        preferenceRepository.save(preference);
        return preference.getId();
    }

    /* 선호 작품 삭제하기 */
    public void removePreferenceNovel(Member member, Long novelId) {
        Preference preference = preferenceRepository.findByMemberAndNovelId(member, novelId)
                .orElseThrow(() -> new NovelServiceException(NOT_FOUND_PREFERENCE_NOVEL));

        Novel novel = preference.getNovel();
        novel.decreaseLikeScore();

        preferenceRepository.delete(preference);
    }

    public Preference findById(Long preferenceId) {
        return preferenceRepository.findById(preferenceId)
                .orElseThrow(() -> new NovelServiceException(NOT_FOUND_PREFERENCE_NOVEL));
    }

    public List<Preference> findByMember(Member member) {
        return preferenceRepository.findByMember(member);
    }

    public List<PreferenceNovelsResponseDto> getPreferenceNovelsResponseDtos(Member member) {
        List<Preference> preferences = findByMember(member);

        List<PreferenceNovelsResponseDto> preferenceNovels = preferences.stream()
                .map(Preference::getNovel)
                .map(novel -> PreferenceNovelsResponseDto.of(novel, member))
                .collect(Collectors.toList());
        return preferenceNovels;
    }

    public Preference findByMemberAndNovelId(Member member, Long novelId) {
        return preferenceRepository.findByMemberAndNovelId(member, novelId)
                .orElseThrow(() -> new NovelServiceException(NOT_FOUND_PREFERENCE_NOVEL));
    }

    public void updateLastRecentChapter(Member member, Long novelId, Integer chapterSequence) {
        Preference preference = findByMemberAndNovelId(member, novelId);
        preference.updateRecentCh(chapterSequence);

    }
}
