package com.example.springrestclient;

import com.example.springrestclient.init.RestClientInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringRestClientApplication implements CommandLineRunner {

    private final RestClientInitializer initializer;

    public SpringRestClientApplication(RestClientInitializer initializer) {
        this.initializer = initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRestClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        RestTemplate restTemplate = new RestTemplate();

        String sessionId = initializer.initializeAndGetSession(restTemplate);

        //todo передать sessionId в POST/PUT/DELETE
    }

}
