package com.example.order.kafka.config;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {
    //수업내용코드는 kafkaProperties빈객체를 주입받지 않고 직접 설정한다.
    //kafkaProperties는 뭐냐면 yml파일에 설정한 카프카 내용을 자동으로 가져와 주는 기능이다.
    //현재 yml에 설정하지 않았기 때문에 직접설정해보자.
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory(KafkaProperties kafkaProperties) {
//        Map<String, Object> props = new HashMap<>(kafkaProperties.buildProducerProperties());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        return new DefaultKafkaProducerFactory<>(props);
//
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
//        return new KafkaTemplate<>(producerFactory);
//    }

    //하나 더 중요한 설정값은 Value serializer다.
    //아직 아웃박스 적용 전이라서 JsonSerializer인데, 이건 이벤트 객체를 직접 카프카에 보낼 때 쓰는 거고,
    //아웃박스처럼 객체를 디비에 String으로 저장하고 그 값을 카프카에 전송할 때는 StringSerializer를 쓴다.
    //현재는 이벤트 객체를 직접 보내기때문에 Json!

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);    //json serializer!
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");

        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));
    }

}
