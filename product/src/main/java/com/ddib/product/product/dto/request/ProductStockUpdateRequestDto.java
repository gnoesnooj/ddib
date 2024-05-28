package com.ddib.product.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductStockUpdateRequestDto {

    @Schema(description = "상품 ID", defaultValue = "1")
    private int productId;

    @Schema(description = "수정할 재고량", defaultValue = "5000")
    private int amount;

}
