package com.otus.storeservice.error;

import lombok.Getter;

@Getter
public class StoreServiceException extends Exception {
    private final int errorCode;
    private final String message;

    public StoreServiceException(ApplicationError applicationError) {
        this(applicationError.getErrorCode(), applicationError.getMessage());
    }

    public StoreServiceException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
