package com.brad.novel.member.service;

import com.brad.novel.member.dto.AuthorResponse;
import com.brad.novel.member.dto.MemberJoinRequestDto;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.exception.MemberNotFoundException;
import com.brad.novel.member.repository.MemberRepository;
import com.brad.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException("찾는 회원이 없습니다!"));
    }
    public Member findByName(String name) {
        Optional<Member> optionalMember = memberRepository.findByName(name);
        return optionalMember.orElseThrow(() -> new MemberNotFoundException("일치하는 회원명이 없습니다!"));
    }

    public AuthorResponse beAuthor(Member member, String nickname) {
        Optional<Member> oMember = memberRepository.findByNickname(nickname);
        if (oMember.isPresent()) {
            return new AuthorResponse("해당 작가명은 사용 중입니다.", nickname);
        }
        Optional<Member> foMember = memberRepository.findById(member.getId());
        foMember.get().addNickname(nickname);
        memberRepository.save(foMember.get());
        addAuthentication(foMember.get());

        return new AuthorResponse(nickname +" 작가님 환영합니다", nickname);
    }

    private void addAuthentication(Member member) {
        MemberContext memberContext = new MemberContext(member, member.genAuthorities());

        UsernamePasswordAuthenticationToken authentication =
                UsernamePasswordAuthenticationToken.authenticated(
                        memberContext,
                        member.getPassword(),
                        memberContext.getAuthorities()
                );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
