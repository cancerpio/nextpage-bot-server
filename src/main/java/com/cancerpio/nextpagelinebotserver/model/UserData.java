package com.cancerpio.nextpagelinebotserver.model;

public class UserData {

    private String userId;
    private String text;
    private String timestamp;
    private String replyToken;
    private String date;

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getText() {
	return text;
    }

    public void setText(String text) {
	this.text = text;
    }

    public String getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(String timestamp) {
	this.timestamp = timestamp;
    }

    public String getReplyToken() {
	return replyToken;
    }

    public void setReplyToken(String replyToken) {
	this.replyToken = replyToken;
    }

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

}