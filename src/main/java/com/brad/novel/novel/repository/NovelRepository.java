package com.brad.novel.novel.repository;

import com.brad.novel.member.entity.Member;
import com.brad.novel.novel.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelRepository extends JpaRepository<Novel, Long> {

}
