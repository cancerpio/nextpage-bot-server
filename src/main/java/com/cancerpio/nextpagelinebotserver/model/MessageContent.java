package com.cancerpio.nextpagelinebotserver.model;

import com.cancerpio.nextpagelinebotserver.constant.RecordType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "about")
@JsonSubTypes({
        @Type(value = MessageContentTrainingLog.class, name = RecordType.TRAININGRECORDS),
        @Type(value = MessageContentDiet.class, name = RecordType.DIET),
        @Type(value = MessageContentChatGpt.class, name = RecordType.CHATGPT)})

public abstract class MessageContent {
}
