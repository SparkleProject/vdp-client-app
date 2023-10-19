package com.example.vdp.connection.controller;

import com.example.vdp.connection.service.HelloWorldClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vdp-connection")
public class VdpHelloWorldController {

    private static final Logger LOG = LoggerFactory.getLogger(VdpHelloWorldController.class);


    @Autowired
    private HelloWorldClient helloWorldClient;

    @GetMapping(value = "/helloworld", produces = "application/json")
    public ResponseEntity<String> get() {
        LOG.info("Connecting to vdp helloworld API.");
        String info = helloWorldClient.getHelloWorld();

        LOG.info("GET /helloworld response:{}", info);
        return ResponseEntity.ok(info);
    }


}
