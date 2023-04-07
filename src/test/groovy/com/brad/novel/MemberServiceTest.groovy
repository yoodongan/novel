package com.brad.novel

import com.brad.novel.member.dto.MemberJoinRequestDto
import com.brad.novel.member.repository.MemberRepository
import com.brad.novel.member.service.MemberService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class MemberServiceTest extends Specification {

    def "회원가입 확인"() {
        setup:
        def name = "김철수"
        def password = "1234"
        def memberJoinRequestDto = new MemberJoinRequestDto()
        memberJoinRequestDto.name = name
        memberJoinRequestDto.password = password

        when:
        def memberRepository = Mock(MemberRepository.class)
        def passwordEncoder = Mock(PasswordEncoder.class)
        def memberService = new MemberService(memberRepository, passwordEncoder)
        then:
        def findName = memberService.join(memberJoinRequestDto)
        findName == name
    }
}
