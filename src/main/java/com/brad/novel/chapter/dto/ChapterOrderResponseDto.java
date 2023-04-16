package com.brad.novel.chapter.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.Data;

@Data
public class ChapterOrderResponseDto {
    private Novel novel;

    private Integer chapterSeq;  // 몇 화인지

    private String subject;

    public ChapterOrderResponseDto(ChapterOrderRequestDto chapterOrderRequestDto) {
        novel = chapterOrderRequestDto.getNovel();
        chapterSeq = chapterOrderRequestDto.getChapterSeq();
        subject = chapterOrderRequestDto.getSubject();
    }
}
