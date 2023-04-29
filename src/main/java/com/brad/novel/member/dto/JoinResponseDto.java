package com.brad.novel.member.dto;

import lombok.Data;

@Data
public class JoinResponseDto {
    private String message;
    private String name;

    public JoinResponseDto(String message, String name) {
        this.message = message;
        this.name = name;
    }
}
