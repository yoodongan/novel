package com.brad.novel.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequestDto {
    @NotEmpty(message = "이름을 입력해주세요!")
    private String name;
    @NotEmpty(message = "비밀번호를 입력해주세요!")
    private String password;
}
