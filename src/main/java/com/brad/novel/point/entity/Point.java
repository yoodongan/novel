package com.brad.novel.point.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
import com.brad.novel.point.exception.NoPointException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Point extends BaseEntity {
    private String preventDupId;
    private Long amount;

    @OneToOne
    private Member member;

    private static String genPreventDupId(Point point, String startTime) {
        return point.getId() + "||" + startTime;
    }
    public static Point create(Long amount, Member member) {
        Point point = new Point();
        point.amount = amount;
        point.member = member;
        return point;
    }
    public void updatePoint(Long minus) {
        if(amount - minus < 0) {
            throw new NoPointException("잔여 포인트가 부족합니다.");
        } else {
            amount -= minus;
        }
    }

    public enum EventType {
        충전_포인트,
        결제_소설단편결제,
        환불_소설단편결제;
    }
}
