package com.brad.novel.member.api;

import com.brad.novel.member.dto.JoinResponse;
import com.brad.novel.member.dto.MemberJoinRequestDto;
import com.brad.novel.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/member", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("/join")
    public JoinResponse join(@RequestBody @Valid MemberJoinRequestDto memberJoinRequestDto) {
        String username = memberService.join(memberJoinRequestDto);
        return new JoinResponse("회원가입 성공", username);
    }
}