package com.ddib.product.product.dto.response;

import com.ddib.product.product.domain.ProductDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailResponseDto {

    @Schema(description = "해당 상품 상세내용의 PK", defaultValue = "1")
    private Long productDetailId;

    @Schema(description = "상세상품 S3저장 경로 URL")
    private String imageUrl;

    public static ProductDetailResponseDto from(ProductDetail productDetail) {
        return ProductDetailResponseDto.builder()
                .imageUrl(productDetail.getImageUrl())
                .productDetailId(productDetail.getProductDetailId())
                .build();
    }

}
