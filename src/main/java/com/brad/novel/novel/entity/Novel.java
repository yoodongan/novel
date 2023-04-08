package com.brad.novel.novel.entity;

import com.brad.novel.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Novel extends BaseEntity {
    private String subject;
    private String genre;
    private String authorName;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;
}
