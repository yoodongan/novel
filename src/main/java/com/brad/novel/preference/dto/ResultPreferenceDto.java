package com.brad.novel.preference.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.Builder;
import lombok.Data;
/* 회원 별 선호작 목록 조회를 위한 Dto */
@Data
@Builder
public class ResultPreferenceDto {
    private Integer lastCh;   // 소설의 마지막 화

    private String subject;
    private String genre;
    private String authorName;
    private String imagePath;

    public static ResultPreferenceDto of(Novel novel) {
        return ResultPreferenceDto.builder()
                .lastCh(novel.getLastCh())
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .authorName(novel.getAuthorName())
                .imagePath(novel.getImagePath())
                .build();
    }
}
