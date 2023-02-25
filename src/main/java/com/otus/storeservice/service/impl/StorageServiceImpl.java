package com.otus.storeservice.service.impl;

import com.otus.storeservice.entity.Order;
import com.otus.storeservice.entity.Storage;
import com.otus.storeservice.error.DishIsMissingException;
import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodDTO;
import com.otus.storeservice.rabbitmq.domain.dto.BookingFoodResponse;
import com.otus.storeservice.rabbitmq.domain.dto.DishDTO;
import com.otus.storeservice.repository.StorageRepository;
import com.otus.storeservice.service.OrderService;
import com.otus.storeservice.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("storageService")
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {
    private final StorageRepository storageRepository;

    private final OrderService orderService;
    @Override
    public BookingFoodResponse bookingFood(BookingFoodDTO bookingFood) {
        try {
            for (DishDTO dish : bookingFood.getDishs()) {
                Storage storage = storageRepository.findById(dish.getMenuId()).orElse(null);
                if ((storage != null) && (storage.getCount() >= dish.getCount())) {
                    storage.setCount(storage.getCount() - dish.getCount());
                    storageRepository.save(storage);
                    orderService.saveOrder(new Order(dish.getMenuId(), storage.getPrice(),  dish.getCount(), bookingFood.getOrderId()));
                } else throw new DishIsMissingException();
            }
            return new BookingFoodResponse("Ok");
        } catch (DishIsMissingException e) {
            return new BookingFoodResponse(e.getMessage());
        } catch (Exception e) {
            return new BookingFoodResponse("Error");
        }
    }

}
