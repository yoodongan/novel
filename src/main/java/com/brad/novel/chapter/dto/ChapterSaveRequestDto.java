package com.brad.novel.chapter.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class ChapterSaveRequestDto {
    @NotEmpty
    private Novel novel;   // 속한 소설이 무엇인지
    @NotEmpty
    private Integer chapterSeq;  // 몇 화인지
    @NotEmpty
    private String subject;  // 제목
    private String shorts;  // 줄거리
    @NotEmpty
    private String content;  // 실제 내용
    private String imagePath;  // 이미지
    private Integer price;
}
