package com.brad.novel.point.dto;

import com.brad.novel.member.entity.Member;
import com.brad.novel.point.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PointRequestDto {
    private Long memberId;
    private Integer amount;

    public Point toPoint(Member member){
        return Point.builder()
                .amount(member.getRestPoint())
                .member(member)
                .build();
    }
}
