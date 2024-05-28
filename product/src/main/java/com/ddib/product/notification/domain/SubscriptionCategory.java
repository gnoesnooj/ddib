package com.ddib.product.notification.domain;

import com.ddib.product.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Schema(description = "구독 카테고리")
public class SubscriptionCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "구독 카테고리 식별키")
    private Integer subscriptionCategoryId;

    @Schema(description = "회원 식별키")
    @ManyToOne
    private User user;

    @Schema(description = "패션 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeFashion;

    @Schema(description = "뷰티 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeBeauty;

    @Schema(description = "식품 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeFood;

    @Schema(description = "가전제품 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeAppliance;

    @Schema(description = "스포츠 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeSports;

    @Schema(description = "생활 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeLiving;

    @Schema(description = "반려동물 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribePet;

    @Schema(description = "여행 카테고리 구독 여부")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean subscribeTravel;
}

