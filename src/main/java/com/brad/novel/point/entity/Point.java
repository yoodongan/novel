package com.brad.novel.point.entity;

import com.brad.novel.base.BaseEntity;
import com.brad.novel.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Point extends BaseEntity {
    private String preventDupId;
    private Long amount;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    private static String genPreventDupId(Point point, String startTime) {
        return point.getId() + "||" + startTime;
    }
    public static Point create(Long amount, Member member) {
        Point point = new Point();
        point.preventDupId = genPreventDupId(point, String.valueOf(LocalDateTime.now()));
        point.amount = amount;
        return point;
    }
}
