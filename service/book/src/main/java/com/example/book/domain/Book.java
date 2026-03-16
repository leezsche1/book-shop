package com.example.book.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "books")
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;
    private Long price;
    private Long reservedQuantity;

    @Version
    private Long version;

    public Book() {}

    public Book(Long price, Long quantity) {
        this.price = price;
        this.quantity = quantity;
        this.reservedQuantity = 0L;
    }

    public Long reserve(Long requestedQuantity) {
        Long reservableQuantity = quantity - reservedQuantity;

        if (reservableQuantity < requestedQuantity) {
            throw new RuntimeException("수량이 부족해요.");
        }

        reservedQuantity += requestedQuantity;

        return price * requestedQuantity;
    }

    public void confirm(Long requestedQuantity) {

        if (quantity < requestedQuantity) {
            throw new RuntimeException("수량이 부족해요.");
        }

        if (reservedQuantity < requestedQuantity) {
            throw new RuntimeException("예약된 수량이 부족해요.");
        }

        this.reservedQuantity -= requestedQuantity;
        this.quantity -= requestedQuantity;

    }

    public void cancel(Long requestedQuantity) {
        if (reservedQuantity < requestedQuantity) {
            throw new RuntimeException("예약된 수량이 부족해요.");
        }

        this.reservedQuantity -= requestedQuantity;

    }

}
