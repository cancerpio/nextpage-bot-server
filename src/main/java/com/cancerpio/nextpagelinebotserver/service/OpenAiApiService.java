package com.cancerpio.nextpagelinebotserver.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class OpenAiApiService {

    @Value("${openai.api.key}")
    private String token;


    public String sendMessage(String pre, String userInputPrompt) {
        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(50));
        final List<ChatMessage> messages = new ArrayList<>();
        StringBuilder answer = new StringBuilder();
        String preface;
        if (pre == null) {
//            preface = "For the below message, please give me your feedback according to some conditions:\n"
//                    + "If it is the description of the training log, please give me the advice for future training, the message type should be \"trainingLog\".\n"
//                    + "If it is about the food, please specify the ingredient and nutrient content of the food being mentioned in the message, the message type should be \"diet\".\n"
//                    + "If it is a question, just answer it in your opinion the message type should be \"question\".\n"
//                    + "Otherwise, just return the original message, the message type should be \"other\".\n"
//                    + "Please reply only to the matched condition.\n"
//                    + "Please reply in the JSON format as below, and write the answer to the key: \"openAIfeedback\", write the message type to the key: \"messageType\".\n"
//                    + "\n"
//                    + "If there has any word in chinese, Please reply in traditional chinese, otherwise, just reply in the language message usage. \n"
//                    + "\n" + "{\n" + "    \"messageType\": “message “type,\n"
//                    + "    \"openAIfeedback\": \"your feedback\"\n" + "}\n" + "";
            preface = "Please answer according to the type of the User message.\n" +
                    "\n" +
                    "If the message is some kind of training log, you are a Strength and Conditioning Coach. Please try to infer the information of action(should be translated in English), action type, weight(in kilograms, just write the number), repetition, set, percentage of repetition maximum, duration, feeling, date(write today if not mentioned), and advice for the next training (write to “advice” property for each action, should be translated in \"繁體中文\" if user input is in Chinese.), then respond with a JSON schema. \n" +
                    "\n" +
                    "For example:\n" +
                    "User: \"I just finished today's training program, deadlift for 3 sets of 5 reps, at about 75% of my 1RM. After that, I went jogging for 1 hour, which made me very tired.\"\n" +
                    "Coach: \n" +
                    "{\n" +
                    "  \"messageContent\":\n" +
                    "  [{\n" +
                    "   \"about\":\"TrainingRecords\",\n" +
                    "   \"action\":\"Deadlift\",\n" +
                    "   \"actionType\":  \"Weight training\",\n" +
                    "   \"weight\": 110,\n" +
                    "   \"repetition\": 5,\n" +
                    "   \"set\": 3,\n" +
                    "   \"percentagOfRepetitionMaximum\": \"75\",\n" +
                    "   \"duration\": null,\n" +
                    "   \"feeling\": null,\n" +
                    "   \"advice\": null, \n" +
                    "   \"date\":\"27/05/2023\" \n" +
                    "},\n" +
                    "{\n" +
                    "  \"about\":\"TrainingRecords\",\n" +
                    "   \"action\":\"Jogging\",\n" +
                    "   \"actionType\":  \"cardio\",\n" +
                    "   \"weight\": null,\n" +
                    "   \"repetition\": null,\n" +
                    "   \"set\": null,\n" +
                    "   \"percentagOfRepetitionMaximum\": null,\n" +
                    "   \"duration\": \"1 hour\",\n" +
                    "   \"feeling\": \"very tired\",\n" +
                    "   \"advice\": null,\n" +
                    "   \"date\":\"27/05/2023\" \n" +
                    "}] \n" +
                    "}\n" +
                    " \n" +
                    "\n" +
                    "If the message is about food and diet, then you are a Nutritionist. Please analyze the protein and fat\n" +
                    "In grams, and calories in kcal then respond with JSON schema.\n" +
                    "For example: \n" +
                    "User:”Steak”\n" +
                    "Nutritionist:\n" +
                    "{ \n" +
                    "  \"messageContent\":\n" +
                    "   [{\n" +
                    "    \"about\":\"Diet\",  \n" +
                    "    \"calories\":250,\n" +
                    "     \"protein\": 35,\n" +
                    "     \"fat\": 10\n" +
                    "  }\n" +
                    " ]\n" +
                    "}\n" +
                    "If the user message is either one of the above types, just respond as ChatGPT at chat.openai.com.\n" +
                    "write the response in JSON schema  like: \n" +
                    "{ \n" +
                    "  \"messageContent\":\n" +
                    "  [{\n" +
                    "    \"about\":\"ChatGPT\",   \n" +
                    "    \"response\":\n" +
                    "  }] \n" +
                    "}\n" +
                    "\n" +
                    "The advice should be translated in Traditional Chinese if user input is in Chinese, otherwise, all the properties in the JSON should be in English.\n" +
                    "Don’t respond to anything beside the json which contains your answer.\n";
        } else preface = pre;
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), preface);
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), userInputPrompt);
        messages.add(systemMessage);
        messages.add(userMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(500)
                .logitBias(new HashMap<>())
                .build();

        try {
            service.createChatCompletion(chatCompletionRequest).getChoices().forEach((t) ->
            {
                String content = t.getMessage().getContent();
                answer.append(content);
                System.out.println(content);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return answer.toString();
    }

}
