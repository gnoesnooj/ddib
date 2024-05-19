package com.ddib.user.user.setting.jwt;


import com.ddib.user.user.domain.User;
import com.ddib.user.user.dto.resposne.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 헤더에서 Authorization 받아오기
        String Authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (Authorization == null || !Authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // authorization 헤더에 토큰이 없으면 다음 필터로 request, response를 넘겨줌

            // 조건이 해당되면 메소드 종료 (필수)
            return;
        }

        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = Authorization.split(" ")[1];

        // 토큰
        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException exception) {
            ObjectMapper mapper = new ObjectMapper();
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");

            ResponseStatusException responseStatusException = new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");

            mapper.writeValue(response.getWriter(), responseStatusException);
            return;
        }

        // 토큰에서 email 획득
        String email = jwtUtil.getEmail(token);

        // User 엔티티 생성한 후 값 set
        User user = User.builder()
                .email(email)
                .build();

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        // 스프링 시큐리티 인증 토큰 생성 (principal, credentials, authorities)
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록 (한번의 요청에 대해 일시적으로 세션 생성)
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
