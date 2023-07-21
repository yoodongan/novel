package com.brad.novel.security.filter;

import com.brad.novel.global.jwt.JwtProvider;
import com.brad.novel.member.entity.Member;
import com.brad.novel.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");  // 토큰 앞에 "Bearer "를 붙이는 것이 관례다.
        if(bearerToken != null) {
            String token = bearerToken.substring(8);// Bearer 이후의 토큰 값만 받아온다.
            if(jwtProvider.verify(token)) {
                Map<String, Object> claims = jwtProvider.getClaims(token);
                String name = (String) claims.get("name");

                Member member = memberService.findByName(name);

                forceAuthentication(member);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void forceAuthentication(Member member) {
        User user = new User(member.getName(), member.getPassword(), member.genAuthorities());

        UsernamePasswordAuthenticationToken authentication = UsernamePasswordAuthenticationToken.authenticated(user, null, member.genAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }
}
