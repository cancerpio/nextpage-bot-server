package com.cancerpio.nextpagelinebotserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value("${spring.kafka.consumer.groupId}")
    private String groupId;
    @Value("${spring.kafka.consumer.autoOffsetReset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, UserData> userDataConsumerFactory() {
	return new DefaultKafkaConsumerFactory<>(consumerProps(), new StringDeserializer(),
		new JsonDeserializer<>(UserData.class));
    }

    @Bean
    // 設定給@kafkaListener的containerFactor 必須自己取個名字,
    // ex: 這邊用的 userDataListenerContainerFactory
    // 否則spring還是會用預設的defaultListenerContainerFactory，造成
    // ...is in unnamed module of loader ... java.lang.String is in module java.base
    // of loader 'bootstrap"錯誤
    ConcurrentKafkaListenerContainerFactory<String, UserData> userDataListenerContainerFactory(
	    ConsumerFactory<String, UserData> consumerFactory) {
	ConcurrentKafkaListenerContainerFactory<String, UserData> factory = new ConcurrentKafkaListenerContainerFactory<>();
	factory.setConsumerFactory(userDataConsumerFactory());
	return factory;
    }

    private Map<String, Object> consumerProps() {
	Map<String, Object> props = new HashMap<>();
	props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
	props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
	props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
	props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetReset);
	// ...
	return props;
    }

}
