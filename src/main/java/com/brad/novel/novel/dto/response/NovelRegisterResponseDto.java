package com.brad.novel.novel.dto.response;

import com.brad.novel.novel.entity.Genre;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.novel.entity.PublishedState;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class NovelRegisterResponseDto {
    private String subject;
    private Genre genre;
    private String authorName;
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    private PublishedState publishedState;
    private String imagePath;

    public static NovelRegisterResponseDto from(Novel novel) {
        return NovelRegisterResponseDto.builder()
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .authorName(novel.getAuthorName())
                .description(novel.getDescription())
                .publishedState(novel.getPublishedState())
                .imagePath(novel.getImagePath())
                .build();
    }
}
