package com.sample.bs23.usermgt.kafka.producer;

import com.sample.bs23.usermgt.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaJsonProducer {

    private final KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User betaUser){
        Message<User> message = MessageBuilder
                .withPayload(betaUser)
                .setHeader(KafkaHeaders.TOPIC, "user-events")
                .build();

        log.info("Message sent to kafka topic");
        kafkaTemplate.send(message);
    }
}
