package com.ddib.user.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "일반회원 수정 요청 DTO")
public class UserModifyRequestDto {
    @Schema(description = "이름")
    private String name;

    @Schema(description = "핸드폰 번호")
    private String phone;

    @Schema(description = "도로명 주소")
    private String roadAddress;

    @Schema(description = "상세 주소")
    private String detailAddress;

    @Schema(description = "우편번호")
    private String zipcode;
}
