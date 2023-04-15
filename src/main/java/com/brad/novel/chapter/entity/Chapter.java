package com.brad.novel.chapter.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.novel.entity.Novel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Chapter extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Novel novel;

    private Integer chapterSeq;  // 몇 화인지
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String shorts;  // 줄거리

    @Column(columnDefinition = "LONGTEXT")
    private String content;  // 실제 내용

    private String imagePath;
    private Integer price;
}
