package com.example.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TossConfirmRequestDTO {

    public String paymentKey;
    public String orderId;
    public Long amount;

}
