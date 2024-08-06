package com.spring.pub_sub_app.controller;

import com.spring.pub_sub_app.dto.User;
import com.spring.pub_sub_app.service.producer.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EventController {

    @Autowired
    private EventProducer producer;

    @GetMapping("/hello")
    public String hello(){
        return "Hello, Hello!";
    }

    @GetMapping("/produce/message/{message}")
    public String sendMessage(@PathVariable String message){
        producer.producePlainMessage(message);
        return "Message Produced!";
    }

    @PostMapping("/produce/user")
    public String sendUser(@RequestBody User user){
        producer.produceUser(user);
        return "User Produced!";
    }

}
