package com.example.springrestclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;

@Component
public class RestClientRunner implements ApplicationRunner {

    private final RestClient restClient;
    private final String URL = "/api/users";
    private final static Logger logger = LoggerFactory.getLogger(RestClientRunner.class);

    public RestClientRunner(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void run(ApplicationArguments args) {

        // 1. GET
        ResponseEntity<String> response = restClient.get()
                .uri(URL)
                .retrieve()
                .toEntity(String.class);

        // Cookie
        String rawCookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        assert rawCookie != null;
        String sessionId = Arrays.stream(rawCookie.split(";"))
                .map(String::trim)
                .filter(c -> c.startsWith("JSESSIONID"))
                .findFirst()
                .orElse(null);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.COOKIE, sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 2. POST
        User user = new User(3L, "James", "Brown", (byte) 30);
        String code1 = restClient.post()
                .uri(URL)
                .headers(h -> h.addAll(headers))
                .body(user)
                .retrieve()
                .body(String.class);

        // 3. PUT
        User updatedUser = new User(3L, "Thomas", "Shelby", (byte) 30);
        String code2 = restClient.put()
                .uri(URL)
                .headers(h -> h.addAll(headers))
                .body(updatedUser)
                .retrieve()
                .body(String.class);

        // 4. DELETE
        String code3 = restClient.delete()
                .uri(URL + "/{id}", user.getId())
                .headers(h -> h.addAll(headers))
                .retrieve()
                .body(String.class);

        logger.info(code1 + code2 + code3);
    }
}
