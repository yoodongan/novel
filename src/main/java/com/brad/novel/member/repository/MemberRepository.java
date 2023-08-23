package com.brad.novel.member.repository;

import com.brad.novel.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);

    Optional<Member> findByNickname(String nickname);

    boolean existsByUsername(String username);
}
