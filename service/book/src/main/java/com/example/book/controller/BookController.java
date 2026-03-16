package com.example.book.controller;

import com.example.book.controller.dto.BookReserveCancelRequestDTO;
import com.example.book.controller.dto.BookReserveConfirmRequestDTO;
import com.example.book.controller.dto.BookReserveRequestDTO;
import com.example.book.controller.dto.BookReserveResponseDTO;
import com.example.book.service.BookFacadeService;
import com.example.book.service.RedisLockService;
import com.example.book.service.dto.BookReserveResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookFacadeService bookFacadeService;
    private final RedisLockService redisLockService;

    @PostMapping("/book/reserve")
    public BookReserveResponseDTO reserve(@RequestBody BookReserveRequestDTO bookReserveRequestDTO) {
        String key = "book:" + bookReserveRequestDTO.getRequestId();
        boolean acquiredLock = redisLockService.tryLock(key, bookReserveRequestDTO.getRequestId());

        if (!acquiredLock) {
            throw new RuntimeException("락 획득에 실패했어요.");
        }

        try {
            BookReserveResultDTO bookReserveResultDTO = bookFacadeService.tryReserve(bookReserveRequestDTO.toBookReserveDTO());
            return new BookReserveResponseDTO(bookReserveResultDTO.getTotalPrice());
        } finally {
            redisLockService.releaseLock(key);
        }
    }

    @PostMapping("/book/confirm")
    public void confirm(BookReserveConfirmRequestDTO bookReserveConfirmRequestDTO) {
        String key = "book:" + bookReserveConfirmRequestDTO.getRequestId();
        boolean acquiredLock = redisLockService.tryLock(key, bookReserveConfirmRequestDTO.getRequestId());

        if (!acquiredLock) {
            throw new RuntimeException("락 획득에 실패했어요.");
        }

        try {
            bookFacadeService.confirmReserve(bookReserveConfirmRequestDTO.toBookReserveConfirmDTO());
        } finally {
            redisLockService.releaseLock(key);
        }
    }

    @PostMapping("/book/cancel")
    public void cancel(BookReserveCancelRequestDTO bookReserveCancelRequestDTO) {
        String key = "book:" + bookReserveCancelRequestDTO.getRequestId();
        boolean acquiredLock = redisLockService.tryLock(key, bookReserveCancelRequestDTO.getRequestId());

        if (!acquiredLock) {
            throw new RuntimeException("락 획득에 실패했어요.");
        }

        try {
            bookFacadeService.cancelReserve(bookReserveCancelRequestDTO.toBookReserveCancelDTO());
        } finally {
            redisLockService.releaseLock(key);
        }
    }
}
