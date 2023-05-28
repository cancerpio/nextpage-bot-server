package com.cancerpio.nextpagelinebotserver.service;

import com.cancerpio.nextpagelinebotserver.MessageContent;
import com.cancerpio.nextpagelinebotserver.OpenAIResponse;
import com.cancerpio.nextpagelinebotserver.UserData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
class KafkaConsumer {
    @Autowired
    LineSignatureValidator lineSignatureValidator;
    @Autowired
    LineMessagingClient lineMessagingClient;
    @Autowired
    OpenAiApiService openAiApiService;
    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(id = "${spring.kafka.consumer.id}", topics = "${spring.kafka.consumer.topic}", containerFactory = "userDataListenerContainerFactory")
    public void UserDataListener(UserData userData) {
        try {
            String replyToken = userData.getReplyToken();
            String text = userData.getText();
            String openAiResponse = openAiApiService.sendMessage(null, text);
            Boolean storeStatus = saveJasonStringToMongo(openAiResponse);
            String replyMessage = "Your message is: " + text + "\nResponse: " + openAiResponse + "\n storeStatus: " + storeStatus;
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, new TextMessage(replyMessage))).get()
                    .getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    Boolean saveJasonStringToMongo(String jsonString) {
        try {
            OpenAIResponse openAIResponse = objectMapper.readValue(jsonString, OpenAIResponse.class);
            List<MessageContent> messageContents = openAIResponse.messageContent;
            messageContents.forEach((messageContent) -> {
                System.out.println("MessageContent: " + messageContent);
            });
        } catch (JsonProcessingException e) {
            System.out.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
        return true;
    }

}