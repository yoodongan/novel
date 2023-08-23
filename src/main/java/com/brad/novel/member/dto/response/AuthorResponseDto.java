package com.brad.novel.member.dto.response;

import lombok.Getter;

@Getter
public class AuthorResponseDto {
    private String name;
    private String nickname;

    public AuthorResponseDto(String name, String nickname) {
        this.name = name;
        this.nickname = nickname;
    }
}
