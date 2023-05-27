package com.cancerpio.nextpagelinebotserver.service;

import com.cancerpio.nextpagelinebotserver.UserData;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
class KafkaConsumer {
    @Autowired
    LineSignatureValidator lineSignatureValidator;
    @Autowired
    LineMessagingClient lineMessagingClient;
    @Autowired
    OpenAiApiService openAiApiService;

    @KafkaListener(id = "${spring.kafka.consumer.id}", topics = "${spring.kafka.consumer.topic}", containerFactory = "userDataListenerContainerFactory")
    public void UserDataListener(UserData userData) {
        try {
            String replyToken = userData.getReplyToken();
            String text = userData.getText();
            String openAiResponse = openAiApiService.sendMessage(null, text);
            String replyMessage = "Your message is: " + text + "\nResponse: " + openAiResponse;
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, new TextMessage(replyMessage))).get()
                    .getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}