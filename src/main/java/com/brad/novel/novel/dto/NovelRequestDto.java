package com.brad.novel.novel.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class NovelRequestDto {
    private String subject;
    private String genre;
    private String authorName;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;
}
