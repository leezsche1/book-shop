package com.example.book.controller.dto;

import com.example.book.service.dto.BookReserveConfirmDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookReserveConfirmRequestDTO {

    public String requestId;

    public BookReserveConfirmDTO toBookReserveConfirmDTO() {
        return new BookReserveConfirmDTO(this.requestId);
    }

}
