package com.example.order.kafka.producer;

import com.example.order.kafka.KafkaTopics;
import com.example.order.kafka.event.OrderConfirmedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderConfirmed(OrderConfirmedEvent event) {
        String key = String.valueOf(event.getOrderId());

        kafkaTemplate.send(KafkaTopics.ORDER_COMPLETED, key, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka order.confirmed 발행 실패. orderId={}", event.getOrderId(), ex);
                    }

                    log.info("Kafka order.confirmed 발행 성공. orderId={}, partition={}, offset={}",
                            event.getOrderId(),
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                });
    }

}
