package com.brad.novel.chapter.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.Data;

@Data
public class ChapterOrderRequestDto {
    private Novel novel;

    private Integer chapterSeq;  // 몇 화인지

    private String subject;

    private Long price;
}
