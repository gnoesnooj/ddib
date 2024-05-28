package com.ddib.product.product.exception;

import lombok.Getter;

@Getter
public class ProductStockShortageException extends RuntimeException {

    private static final String MESSAGE = "[ERROR] 해당 상품의 재고 수량이 부족합니다.";

    public ProductStockShortageException() {
        super(MESSAGE);
    }

}
