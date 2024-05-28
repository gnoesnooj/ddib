package com.ddib.product.product.exception;

public class ProductNotFoundException extends RuntimeException {

    private static final String MESSAGE = "[ERROR] 해당 상품은 존재하지 않습니다.";

    public ProductNotFoundException(){
        super(MESSAGE);
    }

}
