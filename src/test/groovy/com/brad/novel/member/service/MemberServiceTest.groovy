package com.brad.novel.member.service

import com.brad.novel.init.InitService
import com.brad.novel.member.dto.MemberJoinRequestDto
import com.brad.novel.preference.service.PreferenceService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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

    @Autowired
    InitService initService;

    @BeforeEach
    def setup1() {
        initService.init1();
    }

    def "회원가입 확인"() {
        setup:
        def name = "김철수"
        def password = "1234"
        def memberJoinRequestDto = new MemberJoinRequestDto(name, password)

        when:
        def joinName = memberService.join(memberJoinRequestDto)
        then:
        def findMember = memberService.findByName(name);
        findMember.name == name
    }

    def "test1"() {
        given:
        String hello = "안녕"
        def sabarada = " sabarada"

        when:
        def result = "안녕 sabarada"

        then:
        result == "안녕 sabarada"
    }

    /*
    @Rollback(false)
    @Transactional
    def "작가명 입력"() {
        setup:
        def nickname = "히가시노게이고"
        def name = "김철수"
        def password = "1234"
        def memberJoinRequestDto = new MemberJoinRequestDto()
        memberJoinRequestDto.name = name
        memberJoinRequestDto.password = password

        when:
        memberService.join(memberJoinRequestDto)
        def member = memberService.findByName(name)
        memberService.beAuthor(member, nickname)

        def memberContext = customUserDetailsService.loadUserByUsername(name)
        then:
        memberContext.getAuthorities()
        println("hello")
    }
    */
//

}
