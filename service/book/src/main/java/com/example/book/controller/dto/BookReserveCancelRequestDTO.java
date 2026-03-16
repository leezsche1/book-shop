package com.example.book.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookReserveCancelRequestDTO {

    public String requestId;

    public BookReserveCancelDTO toBookReserveCancelDTO() {
        return new BookReserveCancelDTO(this.requestId);
    }

}
