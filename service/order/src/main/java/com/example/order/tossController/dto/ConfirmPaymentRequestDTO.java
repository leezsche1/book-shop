package com.example.order.tossController.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ConfirmPaymentRequestDTO {

    public String paymentKey;
    public String orderId;
    public Long amount;

}
