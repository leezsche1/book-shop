package com.example.order.outbox;

import com.example.order.service.Snowflake;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final Snowflake snowflake;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObjectMapper objectMapper;

    public void publish(OrderConfirmedEventPayload payload) {
        try {

            Outbox outbox = Outbox.create(payload.getOrderId(), objectMapper.writeValueAsString(payload));
            applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
            //이벤트 발생, 참고로 스프링 이벤트다. 이제 스프링은 이걸 받아서 beforecommit이든 aftercommit이든 진행할 예정.
            //beforeCommit, afterCommit같은 기준은 이 이벤트를 발생시킨 트랜젝션이다. 그 트랜젝션이 커밋하기 이전에
            //messageRelay클래스에서 내가 만든 메서드가 실행되는것.

        } catch (JsonProcessingException e) {
            throw new RuntimeException("OrderConfirmedEventPayload 직렬화 실패!", e);
        }
    }

}
