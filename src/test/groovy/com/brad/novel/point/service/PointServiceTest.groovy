package com.brad.novel.point.service


import com.brad.novel.init.InitService
import com.brad.novel.member.service.MemberService
import com.brad.novel.transactions.dto.PointChargeRequestDto
import com.brad.novel.preference.service.PreferenceService
import com.brad.novel.transactions.service.TransactionsService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PointServiceTest extends Specification {
    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PreferenceService preferenceService;
    @Autowired
    TransactionsService transactionsService;

    @Autowired
    InitService initService;

    def "동시에_2개_요청"() throws InterruptedException {
        setup:
        def memberId = 1L
        def findMember = memberService.findById(memberId)
        PointChargeRequestDto requestDto = new PointChargeRequestDto(memberId, 1000L);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 동일 요청 2개
        executorService.execute(() -> {
            transactionsService.addPoint(findMember, requestDto);
        });

        executorService.execute(() -> {
            transactionsService.addPoint(findMember, requestDto);
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
