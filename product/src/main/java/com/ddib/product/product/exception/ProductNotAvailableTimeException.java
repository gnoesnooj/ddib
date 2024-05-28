package com.ddib.product.product.exception;

public class ProductNotAvailableTimeException extends RuntimeException {

    private static final String MESSAGE = "[ERROR] 해당 시간은 이용할 수 없습니다.";

    public ProductNotAvailableTimeException() {
        super(MESSAGE);
    }

}
