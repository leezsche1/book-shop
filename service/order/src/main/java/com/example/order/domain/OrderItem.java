package com.example.order.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "order_items")
@Getter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long bookId;
    private Long quantity;

    public OrderItem() {}

    public OrderItem(Long orderId, Long bookId, Long quantity) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
    }

}
