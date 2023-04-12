package com.brad.novel.preference.repository;

import com.brad.novel.member.entity.Member;
import com.brad.novel.preference.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    List<Preference> findByMemberAndLikeNumberGreaterThan(Member member, int likeNumber);
}
