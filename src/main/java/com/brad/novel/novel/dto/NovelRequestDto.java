package com.brad.novel.novel.dto;

import com.brad.novel.category.entity.Category;
import lombok.Data;

import javax.persistence.Column;

@Data
public class NovelRequestDto {
    private Category category;
    private String subject;
    private String genre;
    private String authorName;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;
}
