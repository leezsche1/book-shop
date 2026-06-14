package com.example.book2.controller.dto;

import com.example.book2.service.dto.BookReserveDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BookReserveRequestDTO {

    public String requestId;
    public List<ReserveItem> items;

    public BookReserveDTO toBookReserveDTO() {
        return new BookReserveDTO(
                this.requestId,
                items.stream().map(
                        item -> new BookReserveDTO.ReserveItem(item.getBookId(), item.getReserveQuantity())
                ).toList()
        );
    }

    @Getter
    @AllArgsConstructor
    public static class ReserveItem {
        Long bookId;
        Long reserveQuantity;
    }

}
