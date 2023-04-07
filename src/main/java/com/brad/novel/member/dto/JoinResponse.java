package com.brad.novel.member.dto;

import lombok.Data;

@Data
public class JoinResponse {
    private String message;
    private String name;

    public JoinResponse(String message, String name) {
        this.message = message;
        this.name = name;
    }
}
