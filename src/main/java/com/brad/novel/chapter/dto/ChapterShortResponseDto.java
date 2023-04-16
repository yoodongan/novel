package com.brad.novel.chapter.dto;

import com.brad.novel.chapter.entity.Chapter;
import lombok.Data;

/* 소설 편 당 조회 시 사용할 Chapter 정보 */
@Data
public class ChapterShortResponseDto {
    private Integer chapterSeq;
    private String subject;

    private String imagePath;
    private Integer price;

    public ChapterShortResponseDto(Chapter chapter) {
        chapterSeq = chapter.getChapterSeq();
        subject = chapter.getSubject();
        imagePath = chapter.getImagePath();
        price = chapter.getPrice();
    }
}
