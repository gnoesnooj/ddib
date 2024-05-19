package com.ddib.user.user.dto.resposne;

import com.ddib.user.user.dto.resposne.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final UserResponseDto userResponseDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return userResponseDto.getName();
    }

    public String getEmail() {
        return userResponseDto.getEmail();
    }
}
