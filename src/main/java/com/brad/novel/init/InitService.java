package com.brad.novel.init;

import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.member.dto.request.MemberJoinRequestDto;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.service.MemberService;
import com.brad.novel.novel.entity.Novel;
import com.brad.novel.preference.entity.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
@RequiredArgsConstructor
public class InitService {
    private final EntityManager em;
    private final MemberService memberService;

    public void init1() {
        MemberJoinRequestDto memberJoinRequestDto = new MemberJoinRequestDto("userABC", "12345");
        Long memberId = memberService.join(memberJoinRequestDto);
        Member member = memberService.findById(memberId);

        Novel novel = new Novel(member, null, "novelA", "스릴러", "베르나르베르베르", 15, "소설A 설명입니다.", null);
        Novel novel2 = new Novel(member, null, "novelB", "로맨스", "베르나르베르베르", 20, "소설B 설명입니다.", null);
        Novel novel3 = new Novel(member, null, "novelC", "스릴러", "베르나르베르베르", 40, "소설C 설명입니다.", null);
        em.persist(novel);
        em.persist(novel2);
        em.persist(novel3);

        Preference preference = new Preference(member, novel, 3);  // 가장 최근 읽은 회차는 쿠키에 저장
        Preference preference2 = new Preference(member, novel2, 2);
        Preference preference3 = new Preference(member, novel3, 4);
        em.persist(preference);
        em.persist(preference2);
        em.persist(preference3);

        Chapter chapter = new Chapter(novel, 1, "1화 제목입니다.", "1화 줄거리입니다.", "1화 실제 내용입니다.", "null", 1500);
        Chapter chapter2 = new Chapter(novel, 2, "2화 제목입니다.", "2화 줄거리입니다.", "2화 실제 내용입니다.", "null", 1500);
        Chapter chapter3 = new Chapter(novel, 3, "3화 제목입니다.", "3화 줄거리입니다.", "3화 실제 내용입니다.", "null", 1500);
        Chapter chapter4 = new Chapter(novel, 4, "4화 제목입니다.", "4화 줄거리입니다.", "4화 실제 내용입니다.", "null", 1500);
        Chapter chapter5 = new Chapter(novel, 5, "5화 제목입니다.", "5화 줄거리입니다.", "5화 실제 내용입니다.", "null", 1500);
        Chapter chapter6 = new Chapter(novel, 6, "6화 제목입니다.", "6화 줄거리입니다.", "6화 실제 내용입니다.", "null", 1500);
        Chapter chapter7 = new Chapter(novel, 7, "7화 제목입니다.", "7화 줄거리입니다.", "7화 실제 내용입니다.", "null", 1500);
        em.persist(chapter);
        em.persist(chapter2);
        em.persist(chapter3);
        em.persist(chapter4);

    }
    public void init2() {
        MemberJoinRequestDto memberJoinRequestDto = new MemberJoinRequestDto("authorABC", "1234");
        Long memberId = memberService.join(memberJoinRequestDto);
        Member member = memberService.findById(memberId);
        member.addNickname("베르나르베르베르");
    }

}
