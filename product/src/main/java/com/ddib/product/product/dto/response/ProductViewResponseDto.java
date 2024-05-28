package com.ddib.product.product.dto.response;

import com.ddib.product.product.domain.Product;
import com.ddib.product.product.domain.ProductCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductViewResponseDto {

    @Schema(description = "상품 ID")
    private Integer productId;

    @Schema(description = "상품명")
    private String name;

    @Schema(description = "전체 재고량")
    private int totalStock;

    @Schema(description = "현재 재고량")
    private int stock;

    @Schema(description = "타임딜 시작시간", defaultValue = "2024-04-25T10:00:00")
    private LocalDateTime eventStartDate;

    @Schema(description = "타임딜 종료시간", defaultValue = "2024-04-25T18:00:00")
    private LocalDateTime eventEndDate;

    @Schema(description = "타임딜 시작시간 - hour", defaultValue = "10")
    private int eventStartTime;

    @Schema(description = "타임딜 종료시간 - hour", defaultValue = "18")
    private int eventEndTime;

    @Schema(description = "가격", defaultValue = "20000")
    private int price;

    @Schema(description = "할인율", defaultValue = "20")
    private double discount;

    @Schema(description = "상품 썸네일 S3저장 경로 URL")
    private String thumbnailImage;

    @Schema(description = "상품 카테고리", defaultValue = "fashion",
            allowableValues = {"Fashion", "Beauty", "Food", "Appliance", "Sports", "Living", "Pet", "Travel"})
    private String category;

    @Schema(description = "상세상품 S3저장 경로 URL 리스트")
    private List<ProductDetailResponseDto> details;

    @Schema(description = "상품 좋아요 수")
    private int likeCount;

    @Schema(description = "판매자 ID", defaultValue = "1")
    private Integer sellerId;

    @Schema(description = "판매회원 이메일")
    private String sellerEmail;

    @Schema(description = "판매자 기업명")
    private String companyName;

    @Schema(description = "판매자 사업자 등록번호")
    private String businessNumber;

    @Schema(description = "대표명")
    private String ceoName;

    @Schema(description = "대표 이메일")
    private String ceoEmail;

    @Schema(description = "대표 전화번호")
    private String ceoPhone;

    @Schema(description = "종료 여부")
    private boolean isOver;

    @Schema(description = "사용자가 해당 상품을 좋아요했는지에 대한 여부")
    private boolean isLiked;

    public static ProductViewResponseDto of(Product product, boolean isLiked) {
        return ProductViewResponseDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .totalStock(product.getTotalStock())
                .stock(product.getStock())
                .eventStartDate(product.getEventStartDate().toLocalDateTime())
                .eventEndDate(product.getEventEndDate().toLocalDateTime())
                .eventStartTime(product.getEventStartTime())
                .eventEndTime(product.getEventEndTime())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .thumbnailImage(product.getThumbnailImage())
                .category(product.getCategory().getValue())
                .details(product.getDetails()
                        .stream()
                        .map(ProductDetailResponseDto::from)
                        .toList()) // dto 변환 필요
                .likeCount(product.getLikedUsers().size())
                .sellerId(product.getSeller().getSellerId())
                .sellerEmail(product.getSeller().getSellerEmail())
                .companyName(product.getSeller().getCompanyName())
                .businessNumber(product.getSeller().getBusinessNumber())
                .ceoName(product.getSeller().getCeoName())
                .ceoEmail(product.getSeller().getCeoEmail())
                .ceoPhone(product.getSeller().getCeoPhone())
                .isOver(product.isOver())
                .isLiked(isLiked)
                .build();
    }
}
