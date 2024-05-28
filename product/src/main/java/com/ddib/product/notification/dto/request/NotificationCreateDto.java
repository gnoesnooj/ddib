package com.ddib.product.notification.dto.request;

import com.ddib.product.product.domain.Product;
import com.ddib.product.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationCreateDto {

    @Schema(description = "회원 식별키")
    @Column(nullable = false)
    private Integer userId;

    @Schema(description = "알림 도메인")
    @Column(nullable = false)
    private String domain;

    @Schema(description = "카테고리")
    private String category;

    @Schema(description = "상품 이름")
    private String productName;

    @Schema(description = "남은 시간")
    private int remainTime;

    public static NotificationCreateDto ofSubscription(Product product, Integer userId){
        return NotificationCreateDto.builder()
                .userId(userId)
                .domain("CATEGORY_PRODUCT_REGISTRATION")
                .category(product.getCategory().getValue())
                .productName(product.getName())
                .remainTime(1)
                .build();
    }

    public static NotificationCreateDto ofFavorite(Product product, Integer userId){
        return NotificationCreateDto.builder()
                .userId(userId)
                .domain("INTEREST_PRODUCT_REMAIN_TIME")
                .category(product.getCategory().getValue())
                .productName(product.getName())
                .remainTime(LocalDateTime.now().getHour() - product.getEventStartTime() == 0 ? 24 : 1)
                .build();
    }
}
