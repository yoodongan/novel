package com.brad.novel.preference.dto.response;

import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.entity.PublishedState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@AllArgsConstructor
@Builder
public class PreferenceNovelsResponseDto {
    private String username;

    private String novelSubject;
    private String novelGenre;
    private String authorName;

    @Enumerated(EnumType.STRING)
    private PublishedState publishedState;

    private Integer totalViewCount;
    private Integer likeScore;

    public static PreferenceNovelsResponseDto of(Novel novel, Member member) {
        return PreferenceNovelsResponseDto.builder()
                .username(member.getUsername())
                .novelSubject(novel.getSubject())
                .novelGenre(novel.getGenre())
                .authorName(novel.getAuthorName())
                .publishedState(novel.getPublishedState())
                .totalViewCount(novel.getTotalViewCount())
                .likeScore(novel.getLikeScore())
                .build();
    }
}
