package com.brad.novel.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class MemberJoinRequestDto {
    @NotEmpty(message = "이름을 입력해주세요!")
    private String name;
    @NotEmpty(message = "비밀번호를 입력해주세요!")
    private String password;
}
