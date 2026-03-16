package com.example.order.controller.dto;

import com.example.order.service.dto.CreateOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreateOrderRequestDTO {

    public Long usePoint;
    public Long priceAmount;
    public List<OrderItem> orderItems;

    public CreateOrderDTO toCreateOrderDTO() {
        return new CreateOrderDTO(
                orderItems.stream().map(
                        orderItem -> new CreateOrderDTO.OrderItem(
                                orderItem.getBookId(), orderItem.getQuantity()
                        )
                ).toList(), this.priceAmount, this.usePoint
        );
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItem{
        public Long bookId;
        public Long quantity;
    }

}
