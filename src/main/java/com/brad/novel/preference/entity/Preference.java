package com.brad.novel.preference.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
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
public class Preference extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Novel novel;

    private int recentCh;  // 가장 마지막으로 읽은 화
    private int likeNumber;      // 좋아요(선호) 개수
}
