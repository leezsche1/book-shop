package com.example.book2.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookReserveDTO {

    public String requestId;
    public List<ReserveItem> items;

    @Getter
    @AllArgsConstructor
    public static class ReserveItem{
        Long bookId;
        Long reserveQuantity;
    }

}
