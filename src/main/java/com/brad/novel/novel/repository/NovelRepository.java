package com.brad.novel.novel.repository;

import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    List<Novel> findAllOrderByLikeScoreDesc();
}
