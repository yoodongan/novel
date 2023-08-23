package com.brad.novel.member.service

import com.brad.novel.member.dto.request.AuthorRequestDto
import com.brad.novel.member.dto.request.MemberJoinRequestDto
import com.brad.novel.member.exception.AlreadyJoinException
import com.brad.novel.preference.service.PreferenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberServiceTest extends Specification {
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PreferenceService preferenceService;

    def "회원가입 확인"() {
        setup:
        def username = "kim519"
        def password = "1234"
        def memberJoinRequestDto = new MemberJoinRequestDto(username, password)

        when:
        def joinId = memberService.join(memberJoinRequestDto)
        then:
        def findMember = memberService.findById(joinId);
        findMember.username == username
    }

    def "동일 회원명(username)으로 가입 시 예외 발생"() {
        setup:
        def memberJoinRequestDto = new MemberJoinRequestDto("kim519", "1234")
        def memberJoinRequestDto2 = new MemberJoinRequestDto("kim519", "1234")

        when:
        memberService.join(memberJoinRequestDto)
        memberService.join(memberJoinRequestDto2)

        then:
        def e = thrown(AlreadyJoinException.class)
        e.message == "동일한 회원명으로 가입했습니다!"
    }

    def "작가명 등록 확인, 작가 권한 체크까지 테스트"() {
        setup:
        def AuthorRequestDto requestDto = new AuthorRequestDto("히가시노게이고")
        def hasAuthority = false  // 작가명 권한이 있는지 체크

        when:
        def member = memberService.findByUsername("userABC")
        memberService.beAuthor(1L, requestDto)
        def authentication = SecurityContextHolder.getContext().getAuthentication()
        def authorities = authentication.getAuthorities()

        then:
        def findMember = memberService.findById(1L);
        findMember.nickname == "히가시노게이고"
        for(def a : authorities) {
            if(a.getAuthority().equals("ROLE_AUTHOR")) {
                hasAuthority = true
                break
            }
        }
        hasAuthority == true
    }
}
