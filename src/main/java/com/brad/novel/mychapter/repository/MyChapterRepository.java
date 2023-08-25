package com.brad.novel.mychapter.repository;

import com.brad.novel.mychapter.entity.MyChapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyChapterRepository extends JpaRepository<MyChapter, Long> {

    @Query("SELECT mc FROM MyChapter mc JOIN FETCH mc.chapter c " +
            "JOIN FETCH c.novel n " +
            "WHERE mc.member.id = :memberId " +
            "AND mc.chapter.id = :chapterId"
    )
    Optional<MyChapter> findByMemberIdAndChapterId(@Param("memberId") Long memberId, @Param("chapterId") Long chapterId);
}
