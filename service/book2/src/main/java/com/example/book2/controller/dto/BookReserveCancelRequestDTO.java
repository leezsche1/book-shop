package com.example.book2.controller.dto;

import com.example.book2.service.dto.BookReserveCancelDTO;
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
