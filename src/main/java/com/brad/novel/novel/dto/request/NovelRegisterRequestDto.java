package com.brad.novel.novel.dto.request;

import com.brad.novel.novel.entity.Genre;
import com.brad.novel.novel.entity.PublishedState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NovelRegisterRequestDto {
    private String subject;  // 소설 제목
    private Genre genre;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;

    private PublishedState publishedState;

    private Integer totalViewCount;

    private Integer totalChapterCount;

    private Integer likeScore;
}
