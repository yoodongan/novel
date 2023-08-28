package com.brad.novel.transactions.dto;

import com.brad.novel.transactions.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TicketChargeRequestDto {
    private Long memberId;
    private Integer amount;
    private Integer needPoint;  // 필요한 포인트
    private Type type;
}
