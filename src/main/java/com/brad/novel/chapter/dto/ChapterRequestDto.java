package com.brad.novel.chapter.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class ChapterRequestDto {
    private Novel novel;

    @NotEmpty
    private Integer chapterSeq;  // 몇 화인지
    @NotEmpty
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String shorts;  // 줄거리

    @Column(columnDefinition = "LONGTEXT")
    private String content;  // 실제 내용

    private String imagePath;

    private Integer price;
}
