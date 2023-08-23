package com.brad.novel.novel.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelDetailResponseDto {
    private String subject; // 소설제목
    private String genre;
    private String description;
    private String imagePath;
    private Integer lastCh;  // 소설의 가장 마지막 화
    private Integer recentCh;  // 유저가 가장 최근 읽은 회차

    public static NovelDetailResponseDto of(Novel novel, Integer recentCh) {
        return NovelDetailResponseDto.builder()
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .description(novel.getDescription())
                .recentCh(recentCh)
                .build();
    }
}
