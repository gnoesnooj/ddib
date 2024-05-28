package com.ddib.product.user.exception;

public class UserNotFoundException extends RuntimeException {

    private static final String MESSAGE = "[ERROR] 해당 유저는 존재하지 않습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

}
