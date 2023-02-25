package com.otus.storeservice.service;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodDTO;
import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodResponse;


public interface OrderService {

    Order saveOrder(Order order);

    void cancelBookingFood(BookingFoodDTO bookingFoodDTO);

}
