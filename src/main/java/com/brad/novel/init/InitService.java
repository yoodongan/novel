package com.brad.novel.init;

import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.member.entity.Member;
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

    public void init1() {
        Member member = new Member("userABC", "12345", 10000L, "authorAA");
        em.persist(member);

        Novel novel = new Novel(member, null, "novelA", "스릴러", "authorAA", 15, "소설A 설명입니다.", null);
        Novel novel2 = new Novel(member, null, "novelB", "로맨스", "authorAA", 15, "소설B 설명입니다.", null);
        Novel novel3 = new Novel(member, null, "novelC", "스릴러", "authorAA", 15, "소설C 설명입니다.", null);
        em.persist(novel);
        em.persist(novel2);
        em.persist(novel3);

        Preference preference = new Preference(member, novel, 4, 3);
        Preference preference2 = new Preference(member, novel2, 2, 2);
        Preference preference3 = new Preference(member, novel3, 6, 4);
        em.persist(preference);
        em.persist(preference2);
        em.persist(preference3);

        Chapter chapter = new Chapter(novel, 1, "1화 제목입니다.", "1화 줄거리입니다.", "1화 실제 내용입니다.", "null", 1500);
        Chapter chapter2 = new Chapter(novel, 2, "2화 제목입니다.", "2화 줄거리입니다.", "2화 실제 내용입니다.", "null", 1500);
        Chapter chapter3 = new Chapter(novel, 3, "3화 제목입니다.", "3화 줄거리입니다.", "3화 실제 내용입니다.", "null", 1500);
        Chapter chapter4 = new Chapter(novel, 4, "4화 제목입니다.", "4화 줄거리입니다.", "4화 실제 내용입니다.", "null", 1500);
        em.persist(chapter);
        em.persist(chapter2);
        em.persist(chapter3);
        em.persist(chapter4);

    }

}
