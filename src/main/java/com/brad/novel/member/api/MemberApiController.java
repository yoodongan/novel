package com.brad.novel.member.api;

import com.brad.novel.common.error.ResponseCode;
import com.brad.novel.common.response.DataResponse;
import com.brad.novel.member.dto.*;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.service.MemberService;
import com.brad.novel.point.dto.PointRequestDto;
import com.brad.novel.point.dto.PointSuccessDto;
import com.brad.novel.point.service.PointService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Tag(name = "MemberApiController", description = "회원가입, 작가회원 등록, 포인트 충전")
@RequestMapping(value = "/members", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {
    private final MemberService memberService;
    private final PointService pointService;

    @PostMapping("/join")
    public DataResponse join(@RequestBody @Valid MemberJoinRequestDto memberJoinRequestDto) {
        Long memberId = memberService.join(memberJoinRequestDto);
        Member member = memberService.findById(memberId);
        return new DataResponse(ResponseCode.SUCCESS_201, new MemberJoinResponseDto(member.getUsername()));
    }
    @PostMapping("/login")
    public DataResponse login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String genAccessToken = memberService.genAccessToken(loginRequest.getName(), loginRequest.getPassword());
        response.addHeader("Authentication", genAccessToken);
        return new DataResponse(ResponseCode.SUCCESS_201, new MemberLoginResponseDto(loginRequest.getName()));
    }

    @PostMapping("/{memberId}/author")
    public DataResponse beAuthor(@PathVariable Long memberId, @RequestBody MemberAuthorDto memberAuthorDto) {
        memberService.beAuthor(memberId, memberAuthorDto.getNickname());
        Member findMember = memberService.findById(memberId);
        return new DataResponse(ResponseCode.SUCCESS_201, new AuthorResponseDto(findMember.getUsername(), findMember.getNickname()));
    }

    @PostMapping("/{memberId}/points")
    public DataResponse addPoint(@PathVariable Long memberId, @RequestBody PointRequestDto requestDto) {
        Member member = memberService.findById(memberId);
        pointService.addPoint(member, requestDto);
        PointSuccessDto pointSuccessDto = new PointSuccessDto(memberId, requestDto.getAmount());
        return new DataResponse(ResponseCode.SUCCESS_201, pointSuccessDto);
    }
}
