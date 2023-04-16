package com.brad.novel.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum ResponseCode {
    ERROR_400("400", "필수 입력값 누락"),
    ERROR_502("502", "잔액 부족!"),
    SUCCESS_201("201", "등록 성공");

    private final String code;
    private final String message;
}
