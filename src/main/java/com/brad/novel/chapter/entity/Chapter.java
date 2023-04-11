package com.brad.novel.chapter.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.novel.entity.Novel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String subject;

    private String shorts;
    private String content;
    private String imagePath;
    private int price;
}
