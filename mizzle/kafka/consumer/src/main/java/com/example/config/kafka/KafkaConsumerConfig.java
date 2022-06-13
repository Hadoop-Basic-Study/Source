package com.example.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.StringOrBytesSerializer;

import com.example.payload.request.SampleRequest;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${custom.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${custom.kafka.group}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, SampleRequest> pushEntityConsumerFactory() {
        JsonDeserializer<SampleRequest> deserializer = entityJsonDeserializer();
        return new DefaultKafkaConsumerFactory<>(consumerFactoryConfig(deserializer),new StringDeserializer(),deserializer);
    }

    private Map<String, Object> consumerFactoryConfig(JsonDeserializer<SampleRequest> deserializer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return props;
    }

    private JsonDeserializer<SampleRequest> entityJsonDeserializer() {
        JsonDeserializer<SampleRequest> deserializer = new JsonDeserializer<>(SampleRequest.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SampleRequest> pushEntityKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SampleRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pushEntityConsumerFactory());
        return factory;
    }

}
