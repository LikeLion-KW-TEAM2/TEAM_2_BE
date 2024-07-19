package com.likelion.hackathon.config.util;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.likelion.hackathon.domain.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.FilterChain;

import lombok.RequiredArgsConstructor;

// 요청 헤더에 토큰이 있는 경우 검증하기 위한 필터
// 검증을 위해 SecurityContextHolder에 강제로 세션을 생성하고 요청이 끝나면 자동으로 소멸됨(config에서 stateless로 설정했기 때문)
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 헤더를 이용하여 authorization정보 추출
        String authorization = request.getHeader("Authorization");

        // authorization정보가 없거나 Bearer로 시작하지 않으면 doFilterInternal를 종료
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // 다음 필터 체인(로그인 필터)으로 넘어감
            return;
        }

        // Bearer를 떼고 토큰 값만 추출
        String token = authorization.split(" ")[1];

        // 토큰의 만료 시간 검증
        if (jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        User user = User.builder()
                .userId(username)
                .password("temp")
                .role(role).build();

        // spring security에서 사용하기 위한 sernamePasswordAuthenticationToken 생성(jwt와는 다른 토큰임)
        Authentication authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        // 세션에 토큰을 통해 사용자를 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}
