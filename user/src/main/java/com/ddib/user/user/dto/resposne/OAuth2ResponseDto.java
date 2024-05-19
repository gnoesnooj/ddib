package com.ddib.user.user.dto.resposne;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public interface OAuth2ResponseDto {
    //이메일
    @NotNull
    String getEmail();
    //사용자 실명 (설정한 이름)
    String getNickName();
}
