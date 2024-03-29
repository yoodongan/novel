package com.brad.novel.novel.repository;

import com.brad.novel.novel.entity.Genre;
import com.brad.novel.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    List<Novel> findAllByOrderByLikeScoreDesc();

    List<Novel> findBySubjectContainingOrAuthorNameContaining(String subject, String author);

    List<Novel> findByGenre(Genre genre);
}
