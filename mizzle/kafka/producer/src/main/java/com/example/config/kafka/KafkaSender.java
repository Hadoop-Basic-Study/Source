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
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.payload.request.SampleRequest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class KafkaSender {
    
    @Autowired
    private KafkaTemplate<String, SampleRequest> kafkaTemplate;

    @Value("${custom.kafka.topic}")
    private String topic;

    public void send(SampleRequest sampleRequest){
        //topic = sampleRequest.getTopic();
        Message<SampleRequest> message = MessageBuilder.withPayload(sampleRequest)
                                                        .setHeader(KafkaHeaders.TOPIC, topic)
                                                        .setHeader(KafkaHeaders.MESSAGE_KEY, topic)
                                                        .build();
        
        ListenableFuture<SendResult<String,SampleRequest>> listenableFuture = kafkaTemplate.send(message);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, SampleRequest>>() {

            @Override
            public void onSuccess(SendResult<String, SampleRequest> stringObjectSendResult) {
                log.info("Sent message=[" + stringObjectSendResult.getProducerRecord().value().toString() +"] with offset=[" + stringObjectSendResult.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                log.warn("Unable to send message=[] due to : " + ex.getMessage());
            }
        });
    }

}
