package com.otus.storeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "order", schema = "store_service", catalog = "postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    public Order(Long menuId, BigDecimal price, Long count, Long orderId) {
        this.menuId = menuId;
        this.price = price;
        this.count = count;
        this.orderId = orderId;
    }
}
