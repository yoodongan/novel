package com.brad.novel.novel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    ACTION("액션"),
    DRAMA("드라마"),
    IMPROVEMENT("자기개발"),
    ROMANCE("로맨스"),
    SCIENCE_FICTION("사이언스픽션"),
    THRILLER("스릴러");

    private String genre;
}
