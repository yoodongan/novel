package com.brad.novel.novel.dto;

import com.brad.novel.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NovelRequestDto {
    private Category category;
    private String subject;  // 소설 제목
    private String genre;
    private String authorName; // 작가명.
    private Integer lastCh;  // 소설 마지막 화
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;
}
