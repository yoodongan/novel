package com.brad.novel.point.service;

import com.brad.novel.member.entity.Member;
import com.brad.novel.member.service.MemberService;
import com.brad.novel.point.entity.Point;
import com.brad.novel.point.exception.PointDuplicatedException;
import com.brad.novel.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointService {
    private final PointRepository pointRepository;
    private final MemberService memberService;

    @Transactional
    public Long addPoint(Long memberId, Long amount) {
        Member findMember = memberService.findById(memberId);
        Point point = Point.create(amount, findMember);

        Optional<Point> oPoint = pointRepository.findByIdLock(point.getPreventDupId());
        oPoint.ifPresent(p -> {
            log.error("중복 충전 발생!");
            throw new PointDuplicatedException("중복 충전 오류 발생!");
            }
        );
        try {
            Thread.sleep(2000); // 충전 중 (2초 소요)
        } catch (InterruptedException e) {
            throw new IllegalStateException("충전 에러 발생!");
        }
        pointRepository.save(point);
        return point.getId();
    }

}
