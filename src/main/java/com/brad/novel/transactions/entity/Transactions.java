package com.brad.novel.transactions.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
import com.brad.novel.transactions.dto.PointChargeRequestDto;
import com.brad.novel.transactions.dto.TicketChargeRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Builder
public class Transactions extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private Integer amount;

    public static Transactions toChargePointTransactions(Member member, PointChargeRequestDto requestDto){
        return Transactions.builder()
                .amount(requestDto.getAmount())
                .member(member)
                .type(Type.POINT_CHARGE)
                .build();
    }

    public static Transactions toChargeTicketTransactions(Member member, TicketChargeRequestDto requestDto){
        return Transactions.builder()
                .amount(requestDto.getAmount())
                .member(member)
                .type(Type.TICKET_CHARGE)
                .build();
    }
}
