package com.brad.novel.novel.dto;

import com.brad.novel.novel.entity.Genre;
import com.brad.novel.novel.entity.Novel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovelBestPreferenceDto {
    private String subject;
    private Genre genre;
    private String authorName;
    private String imagePath;

    public static NovelBestPreferenceDto of(Novel novel) {
        return NovelBestPreferenceDto.builder()
                .subject(novel.getSubject())
                .genre(novel.getGenre())
                .authorName(novel.getAuthorName())
                .imagePath(novel.getImagePath())
                .build();
    }
}
