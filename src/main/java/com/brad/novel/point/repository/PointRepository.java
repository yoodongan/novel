package com.brad.novel.point.repository;

import com.brad.novel.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Point p where p.preventDupId = :preventDupId")
    public Optional<Point> findByIdLock(@Param("preventDupId") String preventDupId);

    Optional<Point> findByMemberId(Long memberId);
}
