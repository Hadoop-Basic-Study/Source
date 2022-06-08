package com.example.config.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.payload.request.SampleRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaMessageListener {

    @KafkaListener(
        topics = "${custom.kafka.topic}",
        groupId = "${custom.kafka.group}",
        containerFactory = "pushEntityKafkaListenerContainerFactory"
    )
    public void listenWithHeader(
        @Payload SampleRequest sampleRequest,
        @Headers MessageHeaders messageHeader
    ){
        
        log.info("{}\n{}",messageHeader.toString(), sampleRequest.toString());
    }
}
