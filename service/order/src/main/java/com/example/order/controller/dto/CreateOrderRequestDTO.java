package com.example.order.controller.dto;

import com.example.order.service.dto.CreateOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequestDTO {

    private Long usePoint;
    private Long priceAmount;
    private List<OrderItem> orderItems;
    private Long userId;

    public CreateOrderDTO toCreateOrderDTO() {
        return new CreateOrderDTO(
                orderItems.stream().map(
                        orderItem -> new CreateOrderDTO.OrderItem(
                                orderItem.getBookId(), orderItem.getQuantity()
                        )
                ).toList(), this.priceAmount, this.usePoint, userId
        );
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem{
        private Long bookId;
        private Long quantity;
    }

}
