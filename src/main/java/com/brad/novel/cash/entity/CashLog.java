package com.brad.novel.cash.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
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
public class CashLog extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member member;

    private int price;
    private Enum eventType;
}
