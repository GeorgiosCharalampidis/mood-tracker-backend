package com.moodtracker.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class AiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getThoughtsFromDeepSeek(String inputPrompt) {
//        System.out.println("Input Prompt:\n" + inputPrompt);
        inputPrompt = inputPrompt + "\n Create a summary of my thoughts. Do you think I am happy or sad? Please talk to me in a" +
                "professional way. Dont mention like specific days, try to make it general. I want to know how I am doing in general.";
        String url = "http://localhost:11434/api/chat";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Map<String, Object> payload = Map.of(
                "model", "deepseek-r1",
                "messages", Collections.singletonList(Map.of("role", "user", "content", inputPrompt))
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                // Ensure UTF-8 encoding
                String responseBody = new String(response.getBody(), StandardCharsets.UTF_8);
                StringBuilder fullResponse = new StringBuilder();

                for (String jsonChunk : responseBody.split("\n")) {
                    JsonNode jsonNode = objectMapper.readTree(jsonChunk);
                    if (jsonNode.has("message") && jsonNode.get("message").has("content")) {
                        fullResponse.append(jsonNode.get("message").get("content").asText());
                    }
                }

                // Remove everything between <think> and </think>, including the tags themselves (multiline support)
                return fullResponse.toString().replaceAll("(?s)<think>.*?</think>", "").trim();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse response from DeepSeek", e);
            }
        } else {
            throw new RuntimeException("Failed to get response from DeepSeek: " + response.getStatusCode());
        }
    }
}