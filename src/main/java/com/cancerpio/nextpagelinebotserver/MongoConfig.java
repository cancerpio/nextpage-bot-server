package com.cancerpio.nextpagelinebotserver;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    String monogoUri;

    public @Bean MongoClient mongoClient() {
        return MongoClients.create(monogoUri);
    }

    @Bean
    MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "nextpage");
    }
}