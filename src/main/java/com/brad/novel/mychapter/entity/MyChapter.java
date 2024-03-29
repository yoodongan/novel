package com.brad.novel.mychapter.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
public class MyChapter extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Chapter chapter;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private boolean isPaid;

    @Column(nullable = false)
    private boolean isRefund;

    @Column(nullable = false)
    private Integer currentPage;

    @Column(nullable = false)
    public boolean isRead;

    public static MyChapter createMyChapter(Member member, Chapter chapter) {
        return MyChapter.builder()
                .isPaid(true)
                .isRefund(false)
                .isRead(false)
                .currentPage(0)
                .member(member)
                .chapter(chapter)
                .build();
    }

    public void changePayStatus() {
        this.isRefund = false;  // false 이면, 환불이 불가능함.
        this.isRead = true;
    }
}
