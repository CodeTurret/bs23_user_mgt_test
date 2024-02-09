package com.sample.bs23.usermgt.kafka.consumer;

import com.sample.bs23.usermgt.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
public class KafkaConsumer {

    /**
     * This method is used for listening events
     * @param betaUser - User
     */
    @KafkaListener(topics = "user-events", groupId = "user_topic_group")
    public void consumeJsonMsg(User betaUser){
        log.info(format("Consume message from user-events topic --->  %s", betaUser.toString()));
    }
}
