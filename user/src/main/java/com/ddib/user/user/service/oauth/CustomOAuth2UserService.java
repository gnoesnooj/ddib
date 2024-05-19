package com.ddib.user.user.service.oauth;

import com.ddib.user.user.dto.resposne.KakaoResponseDto;
import com.ddib.user.user.dto.resposne.OAuth2ResponseDto;
import com.ddib.user.user.domain.User;
import com.ddib.user.user.repository.UserRepository;
import com.ddib.user.user.dto.resposne.CustomOAuth2User;
import com.ddib.user.user.dto.resposne.UserResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2ResponseDto oAuth2Response;

        if (registrationId.equals("kakao")){
            oAuth2Response = new KakaoResponseDto(oAuth2User.getAttributes());
        } else { return null; }

        String email = oAuth2Response.getEmail();
        User existData = userRepository.findByEmail(email);

        if (existData == null) {
            User user = User.builder()
                    .name(oAuth2Response.getNickName())
                    .email(oAuth2Response.getEmail())
                    .build();

            userRepository.save(user);

            UserResponseDto responseDto = UserResponseDto.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();

            return new CustomOAuth2User(responseDto);
        } else {
            UserResponseDto responseDto = UserResponseDto.builder()
                    .name(existData.getName())
                    .email(existData.getEmail())
                    .build();
            return new CustomOAuth2User(responseDto);
        }
    }
}
