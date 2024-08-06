package com.spring.pub_sub_app.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.pub_sub_app.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.common.schema.SchemaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @PulsarListener(subscriptionName = "PubSubAppApplication", topics = "persistent://test/fakhan/messages")
    void consumeMessages(String message){
        log.info("@@@ CONSUMED: {}" , message);
    }

    @PulsarListener(subscriptionName = "PubSubAppApplication", topics = "persistent://test/fakhan/users", schemaType = SchemaType.JSON)
    void consumeUsers(User user) throws JsonProcessingException {
        log.info("### CONSUME: {}" , objectMapper.writeValueAsString(user));
    }
}
