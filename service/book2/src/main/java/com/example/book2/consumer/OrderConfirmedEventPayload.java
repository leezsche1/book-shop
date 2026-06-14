package com.example.book2.consumer;

import com.example.book2.service.dto.BookReserveConfirmDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmedEventPayload {

    private Long orderId;                           //book
    private Long memberId;                          //point
    private Long totalPrice;                        //point
    private List<ReservedItem> reservedItemList;    //bestSeller

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReservedItem{
        public Long bookId;
        public Long reservedQuantity;
    }

    public static BookReserveConfirmDTO toBookReserveConfirmDTO(OrderConfirmedEventPayload payload) {
        return new BookReserveConfirmDTO(String.valueOf(payload.getOrderId()));
    }

}
