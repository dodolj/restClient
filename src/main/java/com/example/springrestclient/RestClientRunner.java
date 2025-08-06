package com.example.springrestclient;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RestClientRunner implements ApplicationRunner {

    private final RestClient restClient;
    private final String URL = "http://94.198.50.185:7081/api/users";

    public RestClientRunner(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void run(ApplicationArguments args) {
        // 1. GET all users and sessionId
        ResponseEntity<String> response = restClient.get()
                .uri(URL)
                .retrieve()
                .toEntity(String.class);

        String sessionId = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

        System.out.println("=== GET Response ===");
        System.out.println(response.getBody());
        System.out.println("=== SESSION ID ===");
        System.out.println(sessionId);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. POST
        User newUser = new User(3L, "James", "Brown", (byte) 30);
        String code1 = restClient.post()
                .uri(URL)
                .headers(h -> h.addAll(headers))
                .body(newUser)
                .retrieve()
                .body(String.class);

        System.out.println("=== POST Response ===");
        System.out.println(code1);

        // 3. PUT
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        String code2 = restClient.put()
                .uri(URL)
                .headers(h -> h.addAll(headers))
                .body(updatedUser)
                .retrieve()
                .body(String.class);

        System.out.println("=== PUT Response ===");
        System.out.println(code2);

        // 4. DELETE
        String code3 = restClient.delete()
                .uri(URL + "/3")
                .headers(h -> h.addAll(headers))
                .retrieve()
                .body(String.class);

        System.out.println("=== DELETE Response ===");
        System.out.println(code3);

        // 5. FINAL
        String finalCode = code1 + code2 + code3;
        System.out.println("=== FINAL Response ===");
        System.out.println(finalCode);
    }
}
