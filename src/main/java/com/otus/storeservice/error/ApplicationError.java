package com.otus.storeservice.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ApplicationError {

    SUCCESS("", 0),
    DISH_IS_MISSING("The dish is missing", 1000),

    INTERNAL_ERROR("Internal error", -9000);

    private final String message;
    private final int errorCode;
}
