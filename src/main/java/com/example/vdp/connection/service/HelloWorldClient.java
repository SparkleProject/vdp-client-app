package com.example.vdp.connection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class HelloWorldClient {

    @Autowired
    private RestTemplate restTemplate;

    public String getHelloWorld() {
        return restTemplate.getForObject("/helloworld", String.class);
    }
}
