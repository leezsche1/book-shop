package com.example.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO {

    private List<OrderItem> orderItems;
    private Long priceAmount;
    private Long usePoint;
    private Long userId;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem {
        Long bookId;
        Long quantity;
    }

    //private과 noargsconstructor의 이유는?

}
