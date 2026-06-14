package com.example.order.kafka.event;

import com.example.order.domain.Order;
import com.example.order.domain.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor      //카프카에서 직렬화/역직렬화 시 빈 객체를 먼저 생성하고 필드 주입을 시도한다고 했다. 그래서 필요한 애너테이션이다.
public class OrderConfirmedEvent {

    private Long orderId;
    private Long userId;
    private Long totalPrice;
    private List<OrderItemPayload> orderItems;
    private LocalDateTime orderedAt;

    public static OrderConfirmedEvent createOrderConfirmedEvent(Order order, List<OrderItem> orderItems) {

        return new OrderConfirmedEvent(
                order.getId(),
                order.getUserId(),
                order.getTotalPrice(),
                orderItems.stream()
                        .map(item -> new OrderItemPayload(
                                item.getBookId(),
                                item.getQuantity()
                        ))
                        .toList(),
                LocalDateTime.now()
        );

    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemPayload {
        private Long bookId;
        private Long quantity;
    }

}
