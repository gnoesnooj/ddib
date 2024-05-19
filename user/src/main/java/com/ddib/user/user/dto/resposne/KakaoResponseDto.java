package com.ddib.user.user.dto.resposne;

import java.util.Map;

public class KakaoResponseDto implements OAuth2ResponseDto {

    private final Map<String, Object> attribute;
    private final Map<String, Object> kakaoAccount;
    private final Map<String, Object> kakaoProfile;

    public KakaoResponseDto(Map<String, Object> attribute) {
        this.attribute = attribute;
        this.kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        this.kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getNickName() {
        return kakaoProfile.get("nickname").toString();
    }
}
