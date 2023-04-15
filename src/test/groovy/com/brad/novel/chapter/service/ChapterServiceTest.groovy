package com.brad.novel.chapter.service

import com.brad.novel.chapter.dto.ChapterRequestDto
import com.brad.novel.init.InitService
import com.brad.novel.member.service.MemberService
import com.brad.novel.novel.service.NovelService
import com.brad.novel.preference.service.PreferenceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChapterServiceTest extends Specification {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PreferenceService preferenceService;
    @Autowired
    NovelService novelService;
    @Autowired
    ChapterService chapterService;

    @Autowired
    InitService initService;

    def "소설(챕터) 작성"() {
        setup:
        def novel = novelService.findById(1L)
        ChapterRequestDto request = new ChapterRequestDto(novel, 1, "소설1-1화의 제목입니다.", "줄거리 입니다", "실제 내용입니다.실제 내용입니다.실제 내용입니다.실제 내용입니다.실제 내용입니다.실제 내용입니다.실제 내용입니다.", null, 1500);
        when:
        chapterService.writeNovel(novel, request);
        then:
        def findChap = chapterService.findBySubject("소설1-1화의 제목입니다.");
        findChap.subject == "소설1-1화의 제목입니다."
    }
}
