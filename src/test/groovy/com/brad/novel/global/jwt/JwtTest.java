package com.brad.novel.global.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class JwtTest {
    @Autowired private JwtProvider jwtProvider;

    @Test
    @DisplayName("secretKey 객체 생성")
    void t1() {
        SecretKey secretKey = jwtProvider.getSecretKey();
        assertThat(secretKey).isNotNull();
    }

    @Test
    @DisplayName("SecretKey 객체는 한번만 생성되어야 한다.")
    void t2() {
        SecretKey secretKey1 = jwtProvider.getSecretKey();
        SecretKey secretKey2 = jwtProvider.getSecretKey();
        assertThat(secretKey1 == secretKey2).isTrue();
    }

    @Test
    @DisplayName("accessToken 을 얻는다.")
    void t3() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("name", "testUser");
        // 5시간의 유효기간을 가지는 토큰을 생성
        String accessToken = jwtProvider.genToken(claims, 60 * 60 * 5); // 60초 * 60 * 5 = 5시간.
        System.out.println("accessToken : " + accessToken);
        assertThat(accessToken).isNotNull();
    }
    @Test
    @DisplayName("accessToken 을 통해서 claims 얻기")
    void t4() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("name", "testUser");

        // 지금으로부터 5시간의 유효기간을 가지는 토큰을 생성
        String accessToken = jwtProvider.genToken(claims, 60 * 60 * 5);
        System.out.println("accessToken : " + accessToken);

        assertThat(jwtProvider.verify(accessToken)).isTrue();
        Map<String, Object> claimsFromToken = jwtProvider.getClaims(accessToken);
        System.out.println("claimsFromToken : " + claimsFromToken);
    }
    @Test
    @DisplayName("만료된 토큰은 유효하지 않다.")
    void t5() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("username", "admin");

        // 유효기간을 -1초로 해서, 이미 유효기간이 끝나도록 설정한 것이다.
        String accessToken = jwtProvider.genToken(claims, -1);
        System.out.println("accessToken : " + accessToken);
        assertThat(jwtProvider.verify(accessToken)).isFalse();
    }
}
