package com.brad.novel.member.dto;

import lombok.Data;

@Data
public class AuthorResponse {
    private String message;
    private String nickname;

    public AuthorResponse(String message, String nickname) {
        this.message = message;
        this.nickname = nickname;
    }
}
