package com.example.order.outbox;

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

}
