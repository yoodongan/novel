package com.brad.novel.novel.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.dto.request.NovelModifyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

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

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(nullable = false)
    private String authorName;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PublishedState publishedState;

    @Column(nullable = false)
    private Integer totalViewCount;

    @Column(nullable = false)
    private Integer totalChapterCount;

    private String imagePath;

    @Column(nullable = false)
    private Integer likeScore;

    public void decreaseLikeScore() {
        if(this.likeScore == 0) return;
        else this.likeScore--;
    }

    public void addAuthorName(String nickname) {
        this.authorName = nickname;
    }

    public void modifyNovel(NovelModifyRequestDto requestDto) {
        this.subject = requestDto.getSubject();
        this.genre = requestDto.getGenre();
        this.description = requestDto.getDescription();
        this.publishedState = requestDto.getPublishedState();
    }

    public void addDefaultImage(String defaultImage) {
        this.imagePath = defaultImage;
    }
}
