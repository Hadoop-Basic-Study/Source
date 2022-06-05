package com.example.config.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.example.payload.request.SampleRequest;


@Component
public class KafkaSender {
    
    @Autowired
    private KafkaTemplate<String, SampleRequest> kafkaTemplate;

    @Autowired
    private CustomListenableFutureCallback customListenableFutureCallback;

    @Value("${custom.kafka.topic}")
    private String topic;

    public void send(SampleRequest sampleRequest){
        Message<SampleRequest> message = MessageBuilder.withPayload(sampleRequest)
                                                        .setHeader(KafkaHeaders.TOPIC, topic)
                                                        .build();
        
        ListenableFuture<SendResult<String,SampleRequest>> listenableFuture = kafkaTemplate.send(message);

        listenableFuture.addCallback(customListenableFutureCallback);
    }

}
