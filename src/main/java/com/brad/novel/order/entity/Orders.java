package com.brad.novel.order.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Orders extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member member;
    @ManyToOne(fetch = LAZY)
    private Chapter chapter;

    private LocalDateTime payDate;
    private LocalDateTime isPaid;
    private LocalDateTime isRefund;
}
