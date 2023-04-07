package com.brad.novel.chapter.entity;

import com.brad.novel.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Chapter extends BaseEntity {
    private String subject;

    private String shorts;
    private String content;
    private String imagePath;
    private int price;
}
