package com.brad.novel.chapter.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.chapter.dto.request.ChapterModifyRequestDto;
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

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String shorts;  // 줄거리

    @Column(columnDefinition = "LONGTEXT")
    private String content;  // 실제 내용

    private Integer chapterSequence; // 몇 화인지.

    private Integer viewCount;  // Chapter 의 조회 수

    private Integer pageCount;  // 해당 챕터의 총 페이지 수

    private String imagePath;

    private Integer ticketPrice;   // 구매하는데 필요한 소장권 수

    public void modifyChapter(ChapterModifyRequestDto requestDto) {
        this.subject = requestDto.getSubject();
        this.shorts = requestDto.getShorts();
        this.content = requestDto.getShorts();
        this.pageCount = requestDto.getPageCount();
        this.chapterSequence = requestDto.getChapterSequence();
        this.imagePath = requestDto.getImagePath();
        this.ticketPrice = requestDto.getTicketPrice();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
