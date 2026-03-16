package com.example.point.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "points")
@Getter
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    private Long amount;
    private Long reservedAmount;

    @Version
    private Long version;

    public Point() {}

    public Point(Long memberId, Long amount, Long reservedAmount) {
        this.memberId = memberId;
        this.amount = amount;
        this.reservedAmount = reservedAmount;
    }

    public void reserve(Long requestedAmount) {
        Long reservableAmount = this.amount - reservedAmount;

        if (reservableAmount < requestedAmount) {
            throw new RuntimeException("금액이 부족해요.");
        }

        reservedAmount += requestedAmount;
    }

    public void confirm(Long requestedAmount) {

        if (this.amount < requestedAmount) {
            throw new RuntimeException("포인트가 부족해요.");
        }

        if (this.reservedAmount < requestedAmount) {
            throw new RuntimeException("요청 금액보다 예약 총액이 낮아요.");
        }

        this.amount -= requestedAmount;
        this.reservedAmount -= requestedAmount;

    }

    public void cancel(Long requestedAmount) {

        if (this.reservedAmount < requestedAmount) {
            throw new RuntimeException("요청 금액보다 예약 총액이 낮아요.");
        }

        this.reservedAmount -= requestedAmount;

    }

}
