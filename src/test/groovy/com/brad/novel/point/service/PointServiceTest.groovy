package com.brad.novel.point.service


import com.brad.novel.init.InitService
import com.brad.novel.member.service.MemberService
import com.brad.novel.point.dto.PointRequestDto
import com.brad.novel.preference.service.PreferenceService
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
    PointService pointService;

    @Autowired
    InitService initService;

    @BeforeEach
    def setup1() {
        initService.init1();
    }

    def "동시에_2개_요청"() throws InterruptedException {
        setup:
        def memberId = 1L
        def findMember = memberService.findById(memberId)
        PointRequestDto pointRequestDto = new PointRequestDto(memberId, 1000L);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 동일 요청 2개
        executorService.execute(() -> {
            pointService.addPoint(findMember, pointRequestDto);
        });

        executorService.execute(() -> {
            pointService.addPoint(findMember, pointRequestDto);
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
