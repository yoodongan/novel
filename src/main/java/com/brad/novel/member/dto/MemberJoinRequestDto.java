package com.brad.novel.member.dto;

import com.brad.novel.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequestDto {
    @NotEmpty(message = "이름을 입력해주세요!")
    private String username;
    @NotEmpty(message = "비밀번호를 입력해주세요!")
    private String password;

    public Member toMember(String encryptedPassword) {
        return Member.builder()
                .username(username)
                .password(encryptedPassword)
                .restPoint(0)
                .restTicket(0)
                .build();
    }
}
