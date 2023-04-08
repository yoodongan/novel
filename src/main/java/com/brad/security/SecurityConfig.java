package com.brad.security;

import com.brad.security.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(
                        formLogin -> formLogin
                                .loginPage("/member/login")
                                .loginProcessingUrl("/member/login")
                                .successHandler(customLoginSuccessHandler())
                                .failureForwardUrl("/member/fail")
                )
                .logout(
                        logout -> logout
                                .logoutUrl("/member/logout")
                );
        return http.build();
    }
    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        // 성공할 때 실행되어야 하는 CustomLoginSuccessHandler 를 빈으로 등록
        return new CustomLoginSuccessHandler();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
