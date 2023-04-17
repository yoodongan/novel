package com.brad.novel.preference.repository;

import com.brad.novel.preference.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    @Query("select p from Preference p "
            +"join fetch p.novel n "
            +"join fetch p.member m "
            +"where p.likeNumber > :likeNumber "
            +"AND m.id = :memberId"
    )
    List<Preference> findByMemberIdAndLikeNumberOverTwo(@Param("memberId") Long memberId, @Param("likeNumber") Integer likeNumber);

    @Query("select n.id, sum(p.likeNumber) AS likeN from Preference p " +
            "join p.member m " +
            "join p.novel n " +
            "group by n.id " +
            "order by likeN desc"
    )
    List<Object[]> findByBestReferencePerHour();
}
