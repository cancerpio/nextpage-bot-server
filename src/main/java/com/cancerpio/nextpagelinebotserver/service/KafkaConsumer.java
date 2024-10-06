package com.cancerpio.nextpagelinebotserver.service;

import com.cancerpio.nextpagelinebotserver.model.MessageContent;
import com.cancerpio.nextpagelinebotserver.model.MessageContentTrainingLog;
import com.cancerpio.nextpagelinebotserver.model.OpenAIResponse;
import com.cancerpio.nextpagelinebotserver.model.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    @Autowired
    MongoTemplate mongoTemplate;

    @KafkaListener(id = "${spring.kafka.consumer.id}", topics = "${spring.kafka.consumer.topic}", containerFactory = "userDataListenerContainerFactory")
    public void UserDataListener(UserData userData) {
        try {
            String replyToken = userData.getReplyToken();
            String userId = userData.getUserId();
            String text = userData.getText();
            String openAiResponse = openAiApiService.sendMessage(null, text);
            boolean storeStatus = saveTrainingDataToMongodb(openAiResponse, userId);
            String replyMessage = "Your message is: " + text + "\nResponse: " + openAiResponse + "\n storeStatus: " + storeStatus;
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, new TextMessage(replyMessage))).get()
                    .getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    boolean saveTrainingDataToMongodb(String jsonString, String userId) {
        boolean saveResult = false;

        try {
            OpenAIResponse openAIResponse = objectMapper.readValue(jsonString, OpenAIResponse.class);
            List<MessageContent> messageContents = openAIResponse.messageContent;
            for (MessageContent messageContent : messageContents) {
                if (messageContent instanceof MessageContentTrainingLog) {
                    ((MessageContentTrainingLog) messageContent).userId = userId;
                    mongoTemplate.insert(messageContent);
                    System.out.println("Training record: " + messageContent + "has been saved");
                    saveResult = true;
                }
            }
        } catch (Exception e) {
            saveResult = false;
            System.out.println(e.getStackTrace());
        }

        return saveResult;
    }

}