package com.example.order.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateOrderResponseDTO {

    public String orderId;
    public String totalPrice;

}
