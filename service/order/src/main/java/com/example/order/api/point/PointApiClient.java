package com.example.order.api.point;

import com.example.order.api.book.dto.BookReserveCancelApiRequestDTO;
import com.example.order.api.point.dto.PointReserveApiRequestDTO;
import com.example.order.api.point.dto.PointReserveCancelApiRequestDTO;
import com.example.order.api.point.dto.PointReserveConfirmApiRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class PointApiClient {

    private final RestClient restClient;

    @Retryable(
            retryFor = { Exception.class },
            noRetryFor = {
                    HttpClientErrorException.BadRequest.class,
                    HttpClientErrorException.NotFound.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void reserve(PointReserveApiRequestDTO pointReserveApiRequestDTO) {
        restClient.post()
                .uri("/point/reserve")
                .body(pointReserveApiRequestDTO)
                .retrieve()
                .toBodilessEntity();
    }

    @Retryable(
            retryFor = { Exception.class },
            noRetryFor = {
                    HttpClientErrorException.BadRequest.class,
                    HttpClientErrorException.NotFound.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public void cancel(PointReserveCancelApiRequestDTO pointReserveCancelApiRequestDTO) {
        restClient.post()
                .uri("/point/cancel")
                .body(pointReserveCancelApiRequestDTO)
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
    public void confirm(PointReserveConfirmApiRequestDTO pointReserveConfirmApiRequestDTO) {
        restClient.post()
                .uri("/point/confirm")
                .body(pointReserveConfirmApiRequestDTO)
                .retrieve()
                .toBodilessEntity();
    }



}
