package com.brad.novel.preference.repository;

import com.brad.novel.member.entity.Member;
import com.brad.novel.preference.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    List<Preference> findByMemberAndLikeNumberGreaterThan(Member member, int likeNumber);

    @Query("select n.id, sum(p.likeNumber) AS likeN from Preference p " +
            "join p.member m " +
            "join p.novel n " +
            "group by n.id " +
            "order by likeN desc"
    )
    List<Object[]> findByBestReferencePerHour();
}
