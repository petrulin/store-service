package com.otus.storeservice.service;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.rabbitmq.domain.dto.CancelDTO;

import java.util.List;


public interface OrderService {

    Order saveOrder(Order order);

    List<Order> findAllByOrderId(CancelDTO cancelDTO);
    void deleteAll(List<Order> orders);

}
