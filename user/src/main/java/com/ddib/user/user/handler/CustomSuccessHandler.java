package com.ddib.user.user.handler;

import com.ddib.user.user.domain.User;
import com.ddib.user.user.dto.resposne.CustomOAuth2User;
import com.ddib.user.user.repository.UserRepository;
import com.ddib.user.user.service.oauth.RedisService;
import com.ddib.user.user.setting.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;

    @Value("${access.token.expiration.time}")
    private Long accessExpireMs;

    @Value("${refresh.token.expiration.time}")
    private Long refreshExpireMs;

    @Value("${releaseHostName}")
    private String releaseHostName;

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String email = customUserDetails.getEmail();

        Integer userId  = userRepository.findUserIdByEmail(email).getUserId();

        User user = userRepository.findByUserId(userId);

        // 권한을 찾아서 권한 설정해줌
        // authentication.getAuthorities()를 호출하여
        // 현재 사용자에게 부여된 권한을 나타내는 GrantedAuthority 객체의 컬렉션을 얻습니다.
        // 이 컬렉션에는 사용자가 가진 모든 권한이 포함되어 있습니다.

        String access = jwtUtil.createJwt("access", email, accessExpireMs);
        String refresh = jwtUtil.createJwt("refresh", email, refreshExpireMs);

        // redis 에 담아서 refresh token 관리
        redisService.setValues(email, refresh, refreshExpireMs);

        response.addCookie(createCookie("refresh", refresh));
        response.addCookie(createCookie("Authorization", access));
        response.addCookie(createCookie("num", String.valueOf(userId)));
        response.addCookie(createCookie("fcm", String.valueOf(user.isSubscribed())));

        response.addHeader("Authorization", "Bearer " + access);

        response.sendRedirect("https://ddib.kro.kr");
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(false);
        return cookie;
    }
}
