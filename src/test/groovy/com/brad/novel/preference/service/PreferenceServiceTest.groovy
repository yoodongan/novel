package com.brad.novel.preference.service

import com.brad.novel.init.InitService
import com.brad.novel.member.service.MemberService
import com.brad.novel.novel.dto.NovelResponseDto
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
class PreferenceServiceTest extends Specification {
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

    def "선호작 목록 조회"() {
        setup:
        def member = memberService.findById(1);
        when:
        def dataResponse = preferenceService.getPreferenceNovel(member, 2) // 선호도가 3이상인 모든 소설 가져오기
        then:
        List<NovelResponseDto> novels = dataResponse.data
        for(NovelResponseDto n : novels) {
            System.println(n.subject)
        }
        novels.size() == 2
    }

}
