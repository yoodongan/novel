package com.brad.novel.chapter.repository;

import com.brad.novel.chapter.entity.Chapter;
import com.brad.novel.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Optional<Chapter> findBySubject(String subject);

    List<Chapter> findAllByNovel(Novel novel);

    boolean existsByNovelAndSubject(Novel novel, String subject);
}
