package com.brad.novel.point.repository;

import com.brad.novel.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface PointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findByPreventDupId(String preventDupId);

    Optional<Point> findByMemberId(Long memberId);

    Optional<Point> findById(Long pointId);
}
