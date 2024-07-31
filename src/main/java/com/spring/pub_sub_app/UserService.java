package com.spring.pub_sub_app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import com.spring.pub_sub_app.PubSubAppApplication.User;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

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
