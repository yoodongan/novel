package com.brad.novel.member.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberJoinRequestDto {
    @NotEmpty(message = "이름을 입력해주세요!")
    private String name;
    @NotEmpty(message = "비밀번호를 입력해주세요!")
    private String password;
}
