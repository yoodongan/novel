package com.brad.novel.member.service;

import com.brad.novel.member.dto.MemberJoinRequestDto;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public String join(MemberJoinRequestDto memberJoinRequestDto) {
        Member member = Member.builder()
                        .name(memberJoinRequestDto.getName())
                        .password(passwordEncoder.encode(memberJoinRequestDto.getPassword()))
                        .build();
        memberRepository.save(member);
        return member.getName();
    }
}
