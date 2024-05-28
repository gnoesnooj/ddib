package com.ddib.product.user.exception;

public class SellerNotFoundException extends RuntimeException {

    private static final String MESSAGE = "[ERROR] 해당 판매자는 존재하지 않습니다.";

    public SellerNotFoundException() {
        super(MESSAGE);
    }

}
