package com.example.order.outbox;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outboxId;
    private Long orderId;
    private String payload;
    private LocalDateTime createdAt;

    public static Outbox create(Long orderId, String payload) {
        Outbox outbox = new Outbox();
        outbox.orderId = orderId;
        outbox.payload = payload;
        outbox.createdAt = LocalDateTime.now();
        return outbox;
    }

}
