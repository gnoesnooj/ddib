package com.ddib.user.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "FCM 토큰 요청 DTO")
public class TokenRequestDto {
    @Schema(description = "일반회원 식별키")
    private Integer userId;

    @Schema(description = "FCM 토큰")
    private String fcmToken;
}
