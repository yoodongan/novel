package com.brad.novel.preference.repository;

import com.brad.novel.member.entity.Member;
import com.brad.novel.preference.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

//    @Query("select p from Preference p "
//            +"join fetch p.novel n "
//            +"join fetch p.member m "
//            +"where p > :likeNumber "
//            +"AND m.id = :memberId"
//    )
//    List<Preference> findByMemberIdAndLikeNumberOverTwo(@Param("memberId") Long memberId, @Param("likeNumber") Integer likeNumber);

    boolean existsByMemberAndNovelId(Member member, Long novelId);

    Optional<Preference> findByMemberAndNovelId(Member member, Long novelId);

    List<Preference> findByMember(Member member);
}
