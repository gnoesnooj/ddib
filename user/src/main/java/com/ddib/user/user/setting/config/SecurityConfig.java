package com.ddib.user.user.setting.config;

import com.ddib.user.user.handler.CustomSuccessHandler;
import com.ddib.user.user.service.oauth.CustomOAuth2UserService;
import com.ddib.user.user.setting.jwt.JWTFilter;
import com.ddib.user.user.setting.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;

    @Value("${releaseHostName}")
    private String releaseHostName;

    //swagger 설정
    private static final String[] AUTH_WHITELIST = {
            "/**", "/api/**", "/graphiql", "/graphql",
            "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html"
    };

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, CustomSuccessHandler customSuccessHandler, JWTUtil jwtUtil, @Value("${releaseHostName}") String releaseHostName) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.releaseHostName = releaseHostName;
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(jwtUtil);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {

                    CorsConfiguration configuration = new CorsConfiguration();

                    configuration.setAllowedOrigins(Collections.singletonList("https://" + releaseHostName));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                    configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);

                    configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                    configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                    return configuration;
                }));

        // csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        // From 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // HTTP Basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // JWTFilter 추가
        http
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        // oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .authorizationEndpoint(redirection -> redirection
                                .baseUri("/api/oauth2/ddib"))
                        .successHandler(customSuccessHandler)
                );

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll()
                        .anyRequest().permitAll());

        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
