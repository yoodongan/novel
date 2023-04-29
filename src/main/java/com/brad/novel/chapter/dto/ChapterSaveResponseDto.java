package com.brad.novel.chapter.dto;

import com.brad.novel.chapter.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ChapterSaveResponseDto {
    private Integer chapterSeq;  // 몇 화인지
    private String subject;  // 제목
    private String shorts;  // 줄거리
    private String content;  // 실제 내용
    private String imagePath;  // 이미지
    private Integer price;

    public static ChapterSaveResponseDto of(Chapter chapter) {
        return ChapterSaveResponseDto.builder()
                .chapterSeq(chapter.getChapterSeq())
                .subject(chapter.getSubject())
                .shorts(chapter.getShorts())
                .content(chapter.getContent())
                .imagePath(chapter.getImagePath())
                .price(chapter.getPrice())
                .build();
    }
}
