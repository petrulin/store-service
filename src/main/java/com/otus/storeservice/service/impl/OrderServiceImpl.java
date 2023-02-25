package com.otus.storeservice.service.impl;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodDTO;
import com.otus.storeservice.repository.OrderRepository;
import com.otus.storeservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void cancelBookingFood(BookingFoodDTO bookingFoodDTO) {
        var orders = orderRepository.findAllByOrderId(bookingFoodDTO.getOrderId());
        orderRepository.deleteAll(orders);
    }

}
