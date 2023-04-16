package com.brad.novel.novel.service

import com.brad.novel.member.dto.MemberJoinRequestDto
import com.brad.novel.member.service.MemberService
import com.brad.novel.novel.dto.NovelRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class NovelServiceTest extends Specification {
    @Autowired
    NovelService novelService;
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;


    def setup() {
        setup:
        def name = "김철수"
        def password = "1234"
        def memberJoinRequestDto = new MemberJoinRequestDto()
        memberJoinRequestDto.name = name
        memberJoinRequestDto.password = password

        def joinName = memberService.join(memberJoinRequestDto)
    }

    def "소설 저장"() {
        setup:
        def novelRequestDto = new NovelRequestDto()
        novelRequestDto.subject = "소설ABC"
        novelRequestDto.genre = "스릴러"
        novelRequestDto.description = "이것은 스릴러 장르로, 마지막에 반전이 숨겨진 흥미진진한 소설입니다."
        novelRequestDto.authorName = "작가A"
        novelRequestDto.imagePath = "sample-img-URL"

        when:
        def novelId = novelService.save(novelRequestDto)
        def findNovel = novelService.findById(novelId)

        then:
        novelRequestDto.subject == findNovel.subject

    }

}
