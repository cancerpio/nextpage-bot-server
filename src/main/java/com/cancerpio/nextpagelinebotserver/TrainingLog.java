package com.cancerpio.nextpagelinebotserver;
//{
//        "action":"Deadlift",
//        "actionType":  "Weight training",
//        "weight": 110,
//        "repetition": 5,
//        "set": 3,
//        "percentagOfRepetitionMaximum": "75",
//        "duration": null,
//        "feeling": null,
//        "advice": null,
//        "date":"27/05/2023"
//        }

public class TrainingLog {
    String action;
    String actionType;
    Integer weight;
    Integer repetition;
    Integer set;
    Integer percentagOfRepetitionMaximum;
    String duration;
    String feeling;
    String advice;
    String date;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getSet() {
        return set;
    }

    public void setSet(int set) {
        this.set = set;
    }

    public int getPercentagOfRepetitionMaximum() {
        return percentagOfRepetitionMaximum;
    }

    public void setPercentagOfRepetitionMaximum(int percentagOfRepetitionMaximum) {
        this.percentagOfRepetitionMaximum = percentagOfRepetitionMaximum;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
