package com.spring.pub_sub_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.spring.pub_sub_app.PubSubAppApplication.User;

@Service
public class UserService {

    private final ObjectMapper objectMapper;

    public UserService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public User singleUser() {

        try {

            User user = new User("1", "Faran");

            return user;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
