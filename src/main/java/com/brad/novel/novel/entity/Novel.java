package com.brad.novel.novel.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.category.entity.Category;
import com.brad.novel.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class Novel extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Category category;

    private String subject;
    private String genre;
    private String authorName;

    private Integer lastCh;   // 마지막화

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private String imagePath;
}
