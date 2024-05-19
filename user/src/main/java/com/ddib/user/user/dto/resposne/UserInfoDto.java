package com.ddib.user.user.dto.resposne;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "일반회원 정보 DTO")
public class UserInfoDto {
    @NotNull
    @Schema(description = "일반회원 식별키")
    private Integer userId;

    @NotNull
    @Schema(description = "이름")
    private String name;

    @NotNull
    @Schema(description = "이메일")
    private String email;

    @Schema(description = "핸드폰 번호")
    private String phone;

    @Schema(description = "도로명 주소")
    private String roadAddress;

    @Schema(description = "상세 주소")
    private String detailAddress;

    @Schema(description = "우편번호")
    private String zipcode;
}
