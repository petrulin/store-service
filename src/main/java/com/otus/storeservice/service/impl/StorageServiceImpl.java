package com.otus.storeservice.service.impl;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.entity.Storage;
import com.otus.storeservice.error.DishIsMissingException;
import com.otus.storeservice.rabbitmq.domain.dto.DishDTO;
import com.otus.storeservice.rabbitmq.domain.dto.TrxDTO;
import com.otus.storeservice.repository.StorageRepository;
import com.otus.storeservice.service.OrderService;
import com.otus.storeservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service("storageService")
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    private final OrderService orderService;
    @Override
    public String bookingFood(TrxDTO trxDTO) {
        try {
            for (DishDTO dish : trxDTO.getOrder().getDishs()) {
                Storage storage = storageRepository.findById(dish.getMenuId()).orElse(null);
                if ((storage != null) && (storage.getCount() >= dish.getCount())) {
                    storage.setCount(storage.getCount() - dish.getCount());
                    storageRepository.save(storage);
                    orderService.saveOrder(new Order(dish.getMenuId(), storage.getPrice(),  dish.getCount(), trxDTO.getOrder().getOrderId()));
                } else throw new DishIsMissingException();
            }
            return "Ok";
        } catch (DishIsMissingException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Error";
        }
    }

    @Override
    public String cancelStorage(List<Order> orders) {
        try {
            for (Order order : orders) {
                Storage storage = storageRepository.findById(order.getMenuId()).orElse(null);
                if (storage != null) {
                    storage.setCount(storage.getCount() + order.getCount());
                    storageRepository.save(storage);
                }
            }
            return "Ok";
        } catch (Exception e) {
            return "Error";
        }
    }

}
