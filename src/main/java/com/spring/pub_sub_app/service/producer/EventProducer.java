package com.spring.pub_sub_app.service.producer;

import com.spring.pub_sub_app.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.pulsar.core.PulsarTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventProducer {

    @Autowired
    private PulsarTemplate<User> userTemplate;
    @Autowired
    private PulsarTemplate<String> messageTemplate;


    public void producePlainMessage(String message){
        var msgId = messageTemplate.send("persistent://test/fakhan/messages", message);
        log.info("@@@ PRODUCED: {}", msgId);
    }

    public void produceUser(User user){
        var msgId = userTemplate.send("persistent://test/fakhan/users", user, Schema.JSON(User.class));
        log.info("### PRODUCED: {}", msgId);
    }
}
