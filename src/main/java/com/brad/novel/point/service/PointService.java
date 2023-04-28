package com.brad.novel.point.service;

import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.repository.MemberRepository;
import com.brad.novel.point.dto.PointRequestDto;
import com.brad.novel.point.entity.Point;
import com.brad.novel.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.brad.novel.common.error.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PointService {
    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void addPoint(Member member, PointRequestDto requestDto) {
        String lockName = "charge-point" + " / " + "name: " + member.getName();
        RLock lock = redissonClient.getLock(lockName);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(0, 3, TimeUnit.SECONDS);
            if (!isLocked) throw new NovelServiceException(NOT_AVAILABLE_LOCK);

            log.info("포인트 충전 중  ... ");
            Thread.sleep(1000); // 의도적 sleep

            Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                    () -> new NovelServiceException(NOT_FOUND_MEMBER)
            );
            if (findMember == null) {
                throw new NovelServiceException(NOT_FOUND_MEMBER);
            }

            findMember.addPoint(requestDto.getAmount());
            log.info("포인트 충전 완료");

        } catch (InterruptedException e) {
            log.error("에러 발생 {} ", e.getMessage(), e);

        } finally {
            if (isLocked) { // 락을 획득했을 때만 unlock 수행
                log.info("(unlock 수행)");
                lock.unlock();
            }
        }
    }
    /*
    @Transactional
    public void addPointWithoutLock(Member member, PointRequestDto requestDto) {

        Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new NovelServiceException(NOT_FOUND_MEMBER)
        );
        findMember.addPoint(requestDto.getAmount());
    }
    */

    public Point findByMemberId(Long memberId) {
        return pointRepository.findByMemberId(memberId).get();
    }
    public void updatePoint(Long memberId, Long amount) {
        Point findPoint = findByMemberId(memberId);
        findPoint.updatePoint(amount);
    }

    public Point findById(Long pointId) {
        return pointRepository.findById(pointId).orElseThrow(() -> new NovelServiceException(NOT_FOUND_POINT));
    }

}
