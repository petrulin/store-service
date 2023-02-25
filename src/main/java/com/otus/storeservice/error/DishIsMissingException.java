package com.otus.storeservice.error;

public class DishIsMissingException extends StoreServiceException {
    public DishIsMissingException() {
        super(ApplicationError.DISH_IS_MISSING);
    }
}
