package com.ddib.product.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductStockDecreaseRequestDto {

    @Schema(description = "상품 ID", defaultValue = "1")
    private int productId;

    @Schema(description = "구매량", defaultValue = "10")
    private int amount;

}
