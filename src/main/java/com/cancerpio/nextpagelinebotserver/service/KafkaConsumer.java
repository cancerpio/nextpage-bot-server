package com.cancerpio.nextpagelinebotserver.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.cancerpio.nextpagelinebotserver.UserData;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.LineSignatureValidator;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;

@Component
class KafkaConsumer {
    @Autowired
    LineSignatureValidator lineSignatureValidator;
    @Autowired
    LineMessagingClient lineMessagingClient;

    @KafkaListener(id = "${spring.kafka.consumer.id}", topics = "${spring.kafka.consumer.topic}", containerFactory = "userDataListenerContainerFactory")
    public void UserDataListener(UserData userData) {
	try {
	    String replyToken = userData.getReplyToken();
	    String text = userData.getText();
	    String replyMessage = "Your message is: " + text;
	    lineMessagingClient.replyMessage(new ReplyMessage(replyToken, new TextMessage(replyMessage))).get()
		    .getMessage();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	} catch (ExecutionException e) {
	    e.printStackTrace();
	}
    }

}