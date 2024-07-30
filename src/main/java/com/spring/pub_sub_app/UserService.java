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

    public User singleUser(){
        return singleUserAsync().join();
    }

    private CompletableFuture<User> singleUserAsync(){
        var request = HttpRequest
                .newBuilder(URI.create("https://random-data-api.com/api/v2/users"))
                .build();

        return HttpClient.newHttpClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(this::convertResponseBodyToUser);
    }

    private User convertResponseBodyToUser(HttpResponse<String> response) {
        try {
            return objectMapper.readValue(response.body(), User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
