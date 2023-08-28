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
public class PointChargeRequestDto {
    private Long memberId;
    private Integer amount;
    private Type type;
}
