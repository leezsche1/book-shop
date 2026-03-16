package com.example.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderResultDTO {

    public Long orderId;
    public Long totalPrice;

}
