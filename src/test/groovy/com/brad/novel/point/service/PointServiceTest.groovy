package com.brad.novel.point.service

import com.brad.novel.init.InitService
import com.brad.novel.member.service.MemberService
import com.brad.novel.preference.service.PreferenceService
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
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

    def "포인트 충전 동시 요청"() {
        setup:
        ExecutorService executorService = Executors.newFixedThreadPool(2)
        CountDownLatch latch = new CountDownLatch (2)

        when:
        executorService.execute({
            pointService.addPoint(1L, 1000)
            latch.countDown()
        })
        executorService.execute({
            pointService.addPoint(1L, 1000)
            latch.countDown()
        })
        latch.await()

        then:
        System.println("끝")

    }
}
