package com.cancerpio.nextpagelinebotserver.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cancerpio.nextpagelinebotserver.UserData;

@Component
class KafkaConsumer {

    @KafkaListener(id = "${spring.kafka.consumer.id}", topics = "${spring.kafka.consumer.topic}", containerFactory = "userDataListenerContainerFactory")
    public void UserDataListener(UserData userData) {

    }

}