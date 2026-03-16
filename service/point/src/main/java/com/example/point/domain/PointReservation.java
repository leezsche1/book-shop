package com.example.point.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "point_reservations")
@Getter
public class PointReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestId;
    private Long pointId;
    private Long reservedAmount;

    @Enumerated(EnumType.STRING)
    private PointReservationStatus status;

    public PointReservation() {}

    public PointReservation(String requestId, Long pointId, Long reservedAmount) {
        this.requestId = requestId;
        this.pointId = pointId;
        this.reservedAmount = reservedAmount;
        this.status = PointReservationStatus.RESERVED;
    }



    public enum PointReservationStatus{
        RESERVED,
        CONFIRMED,
        CANCELLED;
    }
    public void confirm() {
        this.status = PointReservationStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = PointReservationStatus.CANCELLED;
    }
}
