package com.brad.novel.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "MEMBER_001", "회원을 찾을 수 없습니다."),
    NOT_VALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER_002", "잘못된 비밀번호 입니다."),
    ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "MEMBER_003", "이미 존재하는 작가명입니다."),
    ALREADY_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "MEMBER_004", "이미 존재하는 회원명입니다."),

    DUPLICATED_PREFERENCE_NOVEL(HttpStatus.BAD_REQUEST, "PREFERENCE_001", "이미 등록된 선호 작품입니다."),
    NOT_FOUND_PREFERENCE_NOVEL(HttpStatus.NOT_FOUND, "PREFERENCE_002", "선호 작품을 찾을 수 없습니다."),

    NOT_FOUND_NOVEL(HttpStatus.NOT_FOUND, "NOVEL_001", "소설을 찾을 수 없습니다."),
    NOT_FOUND_GENRE(HttpStatus.NOT_FOUND, "GENRE_001", "장르를 찾을 수 없습니다!"),
    NOT_FOUND_CHAPTER(HttpStatus.NOT_FOUND, "CHAPTER_001", "찾을 수 없는 챕터(에피소드)입니다."),
    ALREADY_EXIST_CHAPTER_SUBJECT(HttpStatus.NOT_FOUND, "CHAPTER_002", "이미 존재하는 챕터(에피소드)입니다!"),

    NOT_FOUND_MY_CHAPTER(HttpStatus.NOT_FOUND, "MY_CHAPTER_001", "찾을 수 없는 나의 챕터(에피소드)입니다."),
    DUPLICATED_MY_CHAPTER(HttpStatus.BAD_REQUEST, "MY_CHAPTER_002", "이미 존재하는 나의 챕터(에피소드)입니다."),

    NOT_FOUND_POINT(HttpStatus.NOT_FOUND, "POINT_001", "찾을 수 없는 포인트입니다."),
    LACK_POINT(HttpStatus.BAD_REQUEST, "POINT_001", "포인트가 부족합니다."),

    NOT_AVAILABLE_LOCK(HttpStatus.CONFLICT, "UNAVAILABLE_001", "락 획득이 불가능 합니다."),

    /* 구매 에러 */
    LACK_COUNT_TICKET(HttpStatus.BAD_REQUEST, "UNAVAILABLE_002", "구매에 필요한 소장권이 부족합니다!"),

    /* 성공 */
    SUCCESS_201(HttpStatus.CREATED, "SUCCESS_201", "등록 성공"),
    SUCCESS_202(HttpStatus.OK, "SUCCESS_202", "응답 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
