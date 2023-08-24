package com.brad.novel.novel.dto.response;

import com.brad.novel.novel.entity.Genre;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.entity.PublishedState;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
@Builder
public class NovelResponseDto {
    private String subject;  // 소설 제목
    private Genre genre;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;

    private PublishedState publishedState;

    public static NovelResponseDto from(Novel novel) {
        return NovelResponseDto.builder()
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .description(novel.getDescription())
                .imagePath(novel.getImagePath())
                .build();
    }
}
