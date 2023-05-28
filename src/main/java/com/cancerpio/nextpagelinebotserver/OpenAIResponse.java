package com.cancerpio.nextpagelinebotserver;

import java.util.List;

public class OpenAIResponse {
    String about;
    List<TrainingLog> messageContent;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<TrainingLog> getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(List<TrainingLog> messageContent) {
        this.messageContent = messageContent;
    }
}
