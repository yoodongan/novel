package com.brad.novel.member.service


import com.brad.novel.member.dto.MemberJoinRequestDto
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
        def name = "김철수"
        def password = "1234"
        def memberJoinRequestDto = new MemberJoinRequestDto(name, password)

        when:
        def joinName = memberService.join(memberJoinRequestDto)
        then:
        def findMember = memberService.findByName(name);
        findMember.name == name
    }

    def "회원명 중복 - 에러"() {
        setup:
        def memberJoinRequestDto = new MemberJoinRequestDto("김철수", "1234")
        def memberJoinRequestDto2 = new MemberJoinRequestDto("김철수", "1234")

        when:
        memberService.join(memberJoinRequestDto)
        memberService.join(memberJoinRequestDto2)

        then:
        def e = thrown(AlreadyJoinException.class)
        e.message == "동일한 이름으로 가입했습니다!"
    }

    def "작가명 등록 확인, 작가 권한 체크까지 테스트"() {
        setup:
        def nickname = "히가시노게이고"
        def flag = false  // 작가명 권한이 있는지 체크

        when:
        def member = memberService.findByName("userABC")
        memberService.beAuthor(1L, nickname)
        def authentication = SecurityContextHolder.getContext().getAuthentication()
        def authorities = authentication.getAuthorities()

        then:
        def findMember = memberService.findByName("userABC")
        findMember.nickname == "히가시노게이고"
        for(def a : authorities) {
            if(a.getAuthority().equals("ROLE_AUTHOR")) {
                flag = true
                break
            }
        }
        flag == true
    }
}
