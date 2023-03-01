package com.otus.storeservice.service;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.rabbitmq.domain.dto.TrxDTO;

import java.util.List;


public interface StorageService {

    String bookingFood(TrxDTO trxDTO);
    String cancelStorage(List<Order> orders);
}
