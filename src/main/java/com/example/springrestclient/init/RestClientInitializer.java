package com.example.springrestclient.init;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClientInitializer {

    private final String URL = "http://94.198.50.185:7081/api/users";

    public String initializeAndGetSession(RestTemplate restTemplate) {
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);

        System.out.println("===[ Ответ сервера ]===");
        System.out.println(response.getBody());

        HttpHeaders headers = response.getHeaders();
        String sessionId = headers.getFirst(HttpHeaders.SET_COOKIE);

        System.out.println("===[ SESSION ID ]===");
        System.out.println(sessionId);

        return sessionId;
    }
}