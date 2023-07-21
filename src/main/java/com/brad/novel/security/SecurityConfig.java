package com.brad.novel.security;

import com.brad.novel.security.filter.JwtAuthorizationFilter;
import com.brad.novel.security.handler.CustomLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .antMatchers("/members/login").permitAll()
                .anyRequest().authenticated();
        http
                .csrf().disable()
                .cors().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(null)
                )
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
    @Bean
    public CustomLoginSuccessHandler customLoginSuccessHandler() {
        // 성공할 때 실행되어야 하는 CustomLoginSuccessHandler 를 빈으로 등록
        return new CustomLoginSuccessHandler();
    }

}
