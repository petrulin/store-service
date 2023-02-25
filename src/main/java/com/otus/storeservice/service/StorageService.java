package com.otus.storeservice.service;

import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodDTO;
import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodResponse;


public interface StorageService {

    BookingFoodResponse bookingFood(BookingFoodDTO bookingCourier);
}
