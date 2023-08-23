package com.brad.novel.preference.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/* 누가 어떤 소설을 선호 작품으로 담았는지. */
@Getter
@AllArgsConstructor
public class PreferenceRegisterResponseDto {
    private String username;
    private Long novelId;
}
