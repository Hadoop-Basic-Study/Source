package com.example.config.kafka;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.payload.request.SampleRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomListenableFutureCallback implements ListenableFutureCallback<SendResult<String, SampleRequest>>{
    @Override
    public void onFailure(Throwable ex) {
        // TODO Auto-generated method stub
        log.warn("ListenableFutureCallback onFailure = {}", ex.toString());
    }

    @Override
    public void onSuccess(SendResult<String, SampleRequest> result) {
        // TODO Auto-generated method stub
        log.info("ListenableFutureCallback onSuccess = {}", result.getProducerRecord().toString());
    }
    
}
