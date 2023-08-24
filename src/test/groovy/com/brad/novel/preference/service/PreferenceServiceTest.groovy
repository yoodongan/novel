package com.brad.novel.preference.service

import com.brad.novel.member.service.MemberService
import com.brad.novel.novel.service.NovelService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
    PreferenceService preferenceService;
    @Autowired
    NovelService novelService;

    def "선호 작품 등록"() {
        setup:
        def member = memberService.findById(1L);

        when:
        Long preferenceId = preferenceService.registerPreferenceNovel(member, 1L);

        then:
        def preferenceNovel = preferenceService.findById(preferenceId);
        preferenceNovel.getMember().getId() == 1L
    }

    def "선호 작품 삭제"() {
        setup:
        def member = memberService.findById(1L);

        when:
        Long preferenceId = preferenceService.removePreferenceNovel(member, 2L);

        then:
        def novel = novelService.findById(1L)
        novel.likeScore == 1
    }

    def "유저의 선호작 목록 리스팅"() {
        setup:
        def member = memberService.findById(1L);

        when:
        def responseDtos = preferenceService.getPreferenceNovelsResponseDtos(member) // 선호도가 3이상인 모든 소설 가져오기

        then:
        responseDtos.size() == 2
    }

}
