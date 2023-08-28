package com.brad.novel.transactions.service;

import com.brad.novel.common.exception.NovelServiceException;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.repository.MemberRepository;
import com.brad.novel.transactions.dto.PointChargeRequestDto;
import com.brad.novel.transactions.dto.TicketChargeRequestDto;
import com.brad.novel.transactions.entity.Transactions;
import com.brad.novel.transactions.repository.TransactionsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static com.brad.novel.common.error.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final RedissonClient redissonClient;
    private final MemberRepository memberRepository;

    /* 포인트 충전 */
    @Transactional
    public void addPoint(Member member, PointChargeRequestDto requestDto) {
        String lockName = "charge-point" + " / " + "name: " + member.getUsername();
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

            findMember.addPoint(requestDto.getAmount());
            Transactions transactions = Transactions.toChargePointTransactions(member, requestDto);

            transactionsRepository.save(transactions);
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

    public void addTicket(Member member, TicketChargeRequestDto requestDto) {
        String lockName = "charge-ticket" + " / " + "name: " + member.getUsername();
        RLock lock = redissonClient.getLock(lockName);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(0, 3, TimeUnit.SECONDS);
            if (!isLocked) throw new NovelServiceException(NOT_AVAILABLE_LOCK);

            log.info("소장권 충전 중  ... ");
            Thread.sleep(1000); // 의도적 sleep

            Member findMember = memberRepository.findById(member.getId()).orElseThrow(
                    () -> new NovelServiceException(NOT_FOUND_MEMBER)
            );

            boolean canChargeTicket = canChargeTicket(member, requestDto.getNeedPoint());
            if(canChargeTicket) {
                member.usePoint(requestDto.getNeedPoint());
                findMember.addTicket(requestDto.getAmount());
            }
            Transactions transactions = Transactions.toChargeTicketTransactions(member, requestDto);

            transactionsRepository.save(transactions);
            log.info("소장권 충전 완료");

        } catch (InterruptedException e) {
            log.error("에러 발생 {} ", e.getMessage(), e);

        } finally {
            if (isLocked) { // 락을 획득했을 때만 unlock 수행
                log.info("(unlock 수행)");
                lock.unlock();
            }
        }
    }

    private boolean canChargeTicket(Member member, Integer needPoint) {
        if(member.getRestPoint() < needPoint) {
            throw new NovelServiceException(LACK_POINT);
        }
        return true;
    }
}
