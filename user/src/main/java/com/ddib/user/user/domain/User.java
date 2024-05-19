package com.ddib.user.user.domain;

import com.ddib.user.user.dto.request.UserModifyRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Slf4j
@Schema(description = "일반회원")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "일반회원")
    private Integer userId;

    @Schema(description = "이름")
    @Column(nullable = false)
    private String name;

    @Schema(description = "이메일")
    @Column(nullable = false)
    private String email;

    @Schema(description = "핸드폰 번호")
    private String phone;

    @Schema(description = "도로명 주소")
    private String roadAddress;

    @Schema(description = "상세 주소")
    private String detailAddress;

    @Schema(description = "우편번호")
    private String zipcode;

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    @Schema(description = "구독 여부")
    private boolean isSubscribed;

    @Schema(description = "FCM 토큰")
    private String fcmToken;

    public void updateInfo(UserModifyRequestDto requestDto) {
        this.name = requestDto.getName();
        this.phone = requestDto.getPhone();
        this.roadAddress = requestDto.getRoadAddress();
        this.detailAddress = requestDto.getDetailAddress();
        this.zipcode = requestDto.getZipcode();
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
