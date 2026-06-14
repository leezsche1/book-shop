package com.example.book2.consumer;

import com.example.book2.service.BookService;
import com.example.book2.service.dto.BookReserveConfirmDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConfirmedEventConsumer {

    private final BookService bookService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "order-confirmed",
            groupId = "book-service"
    )
    public void consume(String message, Acknowledgment ack) {
        log.info("[OrderConfirmedEventConsumer.consume] message={}", message);

        try {
            BookReserveConfirmDTO bookReserveConfirmDTO = OrderConfirmedEventPayload.toBookReserveConfirmDTO(
                    objectMapper.readValue(message, OrderConfirmedEventPayload.class)
            );

            bookService.confirmReserveAtomicUpdate(bookReserveConfirmDTO);
            ack.acknowledge();

        } catch (JsonProcessingException e) {
            log.error("역직렬화 실패 message = {}", message, e);
            //ack잘못된 메세지를 ack하지 않으면 계속해서 그 이벤트를 읽겠지만, 아직 ack하지 않는다.
            //오류 추적을 위해, 추후 confirm 직렬화 실패 상태를 만들 수도 있을 것이다.
        } catch (Exception e) {
            log.error("처리 실패 message = {}", message, e);
            throw e;
            //ack하지 않는다.
        }

    }

}
