package com.brad.novel.preference.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.entity.Novel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@SuperBuilder
public class Preference extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Novel novel;

    private Integer recentCh;  // 유저가 마지막으로 읽은 챕터 ex). 전체 10화 중 2화 읽었다.

    public static Preference createPreferenceNovel(Member member, Novel novel) {
        return Preference.builder()
                .member(member)
                .novel(novel)
                .recentCh(0)
                .build();
    }
    public void updateRecentCh(Integer chapterSequence) {
        this.recentCh = chapterSequence;
    }
}
