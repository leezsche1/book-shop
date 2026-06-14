package com.example.order.api.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookReserveApiRequestDTO {

    public String requestId;
    public List<ReserveItem> items;

    @Getter
    @AllArgsConstructor
    public static class ReserveItem {
        Long bookId;
        Long reserveQuantity;
    }

}
