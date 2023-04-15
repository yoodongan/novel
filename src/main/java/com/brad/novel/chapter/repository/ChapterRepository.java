package com.brad.novel.chapter.repository;

import com.brad.novel.chapter.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Optional<Chapter> findBySubject(String subject);
}
