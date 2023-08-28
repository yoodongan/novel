package com.brad.novel.transactions.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    POINT_CHARGE("포인트 충전"),
    TICKET_CHARGE("소장권 충전"), // 포인트 사용하는 것과 동일.
    TICKET_USE("소장권 사용");

    private final String typeName;
}
