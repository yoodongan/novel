package com.brad.novel.preference.service;

import com.brad.novel.common.response.DataResponse;
import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.dto.NovelResponseDto;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.preference.entity.Preference;
import com.brad.novel.preference.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public DataResponse getPreferenceNovel(Member member, int likeNumber) {
        List<Preference> preferences = preferenceRepository.findByMemberAndLikeNumberGreaterThan(member, likeNumber);
        List<Novel> novels = preferences.stream().map(Preference::getNovel).collect(Collectors.toList());
        List<NovelResponseDto> collect = novels.stream()
                .map((novel) -> NovelResponseDto.of(novel))
                .collect(Collectors.toList());
        return DataResponse.success(collect);
    }


}
