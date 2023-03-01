package com.otus.storeservice.service.impl;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.rabbitmq.domain.dto.CancelDTO;
import com.otus.storeservice.repository.OrderRepository;
import com.otus.storeservice.service.OrderService;
import com.otus.storeservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("orderService")
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findAllByOrderId(CancelDTO cancelDTO) {
        return orderRepository.findAllByOrderId(cancelDTO.getOrderId());
    }

    @Override
    public void deleteAll(List<Order> orders) {
        orderRepository.deleteAll(orders);
    }

}
