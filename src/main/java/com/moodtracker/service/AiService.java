package com.moodtracker.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.errors.RateLimitException;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final OpenAIClient openAiClient;

    public AiService(@Value("${openai.api.key}") String apiKey) {
        this.openAiClient = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String summarizeThoughts(String thoughts) {
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("Summarize the following thoughts: " + thoughts)
                .model(ChatModel.GPT_4O_MINI_2024_07_18)
                .build();
        try {
            ChatCompletion chatCompletion = openAiClient.chat().completions().create(params);
            return chatCompletion.choices().get(0).message().toString();
        } catch (RateLimitException e) {
            return "Rate limit exceeded. Please try again later.";
        }
    }
}