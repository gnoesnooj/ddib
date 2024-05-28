package com.ddib.product.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProductMainResponseDto {

    @Schema(description = "당일 종료되지 않은 타임딜 상품 3개")
    private List<ProductResponseDto> todayNotOverProducts;

    @Schema(description = "당일 모든 타임딜 상품 전체 리스트")
    private List<ProductResponseDto> todayProducts;

}
