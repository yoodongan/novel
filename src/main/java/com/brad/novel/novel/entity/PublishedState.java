package com.brad.novel.novel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public enum PublishedState {
    PUBLISHED("연재"),
    NOT_PUBLISHED("휴재"),
    FINISHED("연재 끝")
    ;
    private String status;
}