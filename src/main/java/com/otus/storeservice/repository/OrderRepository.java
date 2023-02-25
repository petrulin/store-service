package com.otus.storeservice.repository;

import com.otus.storeservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderId(Long orderId);
  //  findAllByDeliveryDateAndDeliveryHourAndOrderId

 //   List<CourierSchedule> findAllByDeliveryDateAndDeliveryHour(LocalDate deliveryDate, Long deliveryHour);

}
