package com.brad.novel.chapter.dto.response;

import com.brad.novel.chapter.entity.Chapter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChapterDetailResponseDto {
    private String novelSubject; // 소설 제목
    private String subject; // 챕터 제목
    private String shorts;  // 간략 줄거리 소개
    private String content;  // 실제 내용

    public static ChapterDetailResponseDto of(Chapter chapter, String novelSubject) {
        return ChapterDetailResponseDto.builder()
                .novelSubject(novelSubject)
                .subject(chapter.getSubject())
                .shorts(chapter.getShorts())
                .content(chapter.getContent())
                .build();
    }
}
