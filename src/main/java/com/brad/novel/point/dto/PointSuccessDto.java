package com.brad.novel.point.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointSuccessDto {
    private Long memberId;
    private Long amount;
}
