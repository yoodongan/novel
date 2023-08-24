package com.brad.novel.novel.entity;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.exception.NovelServiceException;
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

    private String koreaGenre;

    public static Genre getGenre(String koreaGenre) {
        return switch(koreaGenre) {
            case "액션" -> ACTION;
            case "드라마" -> DRAMA;
            case "자기개발" -> IMPROVEMENT;
            case "로맨스" -> ROMANCE;
            case "사이언스픽션" -> SCIENCE_FICTION;
            case "스릴러" -> THRILLER;
            default -> throw new NovelServiceException(ResponseCode.NOT_FOUND_GENRE);
        };
    }
}
