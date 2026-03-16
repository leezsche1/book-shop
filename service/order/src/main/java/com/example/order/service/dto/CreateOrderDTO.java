package com.example.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderDTO {

    public List<OrderItem> orderItems;
    public Long priceAmount;
    public Long usePoint;

    @Getter
    @AllArgsConstructor
    public static class OrderItem {
        Long bookId;
        Long quantity;
    }

}
