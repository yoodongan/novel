package com.brad.novel.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class AuthorRequestDto {
    @NotEmpty(message = "작가명을 입력해주세요!")
    private String nickname;
}
