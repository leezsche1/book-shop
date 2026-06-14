package com.example.order.api.book;

import com.example.order.api.book.dto.BookReserveApiRequestDTO;
import com.example.order.api.book.dto.BookReserveApiResponseDTO;
import com.example.order.api.book.dto.BookReserveCancelApiRequestDTO;
import com.example.order.api.book.dto.BookReserveConfirmApiRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class BookApiClient {

    private final RestClient restClient;

    @Retryable(
            retryFor = {Exception.class},
            noRetryFor = {
                    HttpClientErrorException.BadRequest.class,
                    HttpClientErrorException.NotFound.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public BookReserveApiResponseDTO reserve(BookReserveApiRequestDTO bookReserveApiRequestDTO) {
        return restClient.post()
                .uri("/book/reserve")
                .body(bookReserveApiRequestDTO)
                .retrieve()
                .body(BookReserveApiResponseDTO.class);
    }

    @Retryable(
            retryFor = {Exception.class},
            noRetryFor = {
                    HttpClientErrorException.BadRequest.class,
                    HttpClientErrorException.NotFound.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void cancel(BookReserveCancelApiRequestDTO bookReserveCancelApiRequestDTO) {
        restClient.post()
                .uri("/book/cancel")
                .body(bookReserveCancelApiRequestDTO)
                .retrieve()
                .toBodilessEntity();
    }

    @Retryable(
            retryFor = {Exception.class},
            noRetryFor = {
                    HttpClientErrorException.BadRequest.class,
                    HttpClientErrorException.NotFound.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void confirm(BookReserveConfirmApiRequestDTO bookReserveConfirmApiRequestDTO) {
        restClient.post()
                .uri("/book/confirm")
                .body(bookReserveConfirmApiRequestDTO)
                .retrieve()
                .toBodilessEntity();
    }
}
