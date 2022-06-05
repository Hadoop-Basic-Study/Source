package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.config.kafka.KafkaSender;
import com.example.payload.request.SampleRequest;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Autowired
    private KafkaSender kafkaSender;

    @PostMapping
    public String sendMessage(@RequestBody SampleRequest sampleRequest) {
        kafkaSender.send(sampleRequest);
        return "success";
    }

}