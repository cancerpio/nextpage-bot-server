package com.cancerpio.nextpagelinebotserver.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("action_record")
public class MessageContentTrainingLog extends MessageContent {
    //         "about":"TrainingRecords",
//         "action":"Deadlift",
//         "actionType":  "Weight training",
//         "weight": 110,
//         "repetition": 5,
//         "set": 3,
//         "percentagOfRepetitionMaximum": "75",
//         "duration": null,
//         "feeling": null,
//         "advice": null,
//         "date":"27/05/2023"
    public String userId;
    public String action;
    public String actionType;
    public Integer weight;
    public Integer repetition;
    public Integer set;
    public Integer percentagOfRepetitionMaximum;
    public String duration;
    public String feeling;
    public String advice;
    public String date;

}
