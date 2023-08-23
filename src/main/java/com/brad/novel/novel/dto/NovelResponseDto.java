package com.brad.novel.novel.dto;

import com.brad.novel.novel.entity.Novel;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class NovelResponseDto {
    private String subject;
    private Integer totalChapterCount;
    private String genre;
    private String authorName;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;

    public static NovelResponseDto of(Novel novel) {
        return NovelResponseDto.builder()
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .authorName(novel.getAuthorName())
                .description(novel.getDescription())
                .build();
    }
}
