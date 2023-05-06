package com.cancerpio.nextpagelinebotserver;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;

@Configuration
public class LinebotConfig {

    @Value("${line.bot.channelToken}")
    private String channelToken;
    @Value("${line.bot.channelSecret}")
    private String channelSecret;

    @Bean
    LineSignatureValidator lineSignatureValidator() {
	return new LineSignatureValidator(channelSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    LineMessagingClient lineMessagingClient() {
	return LineMessagingClient.builder(channelToken).build();
    }
}
