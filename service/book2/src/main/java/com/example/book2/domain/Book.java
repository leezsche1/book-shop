package com.example.book2.domain;

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
//    private Long reservedQuantity;


    public Book() {}

    public Book(Long price, Long quantity) {
        this.price = price;
        this.quantity = quantity;
//        this.reservedQuantity = 0L;
    }

    public void confirm(Long requestedQuantity) {

        if (this.quantity < requestedQuantity) {
            throw new RuntimeException("수량이 부족해요.");
        }
        this.quantity -= requestedQuantity;

    }

}
