package com.example.book.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "book_reservations")
@Getter
public class BookReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestId;
    private Long bookId;
    private Long reservedQuantity;
    private Long reservedPrice;

    @Enumerated(EnumType.STRING)
    private BookReservationStatus status;

    public BookReservation() {}

    public BookReservation(String requestId, Long bookId, Long reservedQuantity, Long reservedPrice) {
        this.requestId = requestId;
        this.bookId = bookId;
        this.reservedQuantity = reservedQuantity;
        this.reservedPrice = reservedPrice;
        this.status = BookReservationStatus.RESERVED;
    }

    public enum BookReservationStatus {
        RESERVED,
        CONFIRMED,
        CANCELLED;
    }
    public void confirm() {
        this.status = BookReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = BookReservationStatus.CANCELLED;
    }

}
