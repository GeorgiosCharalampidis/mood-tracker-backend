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
        inputPrompt = inputPrompt + "\n You are an AI that reads and responds to the user's daily thoughts. " +
                "Your goal is not to provide generic advice, but to engage with their ideas in a meaningful way. Your responses should feel like a thoughtful conversation, acknowledging their perspective, questioning assumptions, and offering insights rather than solutions. You should:\n" +
                "Identify the main themes or emotions in the user's thoughts.\n" +
                "Respond with depth, as if you're having a real discussion, not just summarizing.\n" +
                "Challenge ideas when necessary, but in a way that encourages reflection, not argument.\n" +
                "Avoid clichés or overly positive encouragement unless it feels natural.\n" +
                "Ask open-ended questions to keep the conversation going.\n" +
                "The user does not expect structured self-improvement advice but rather a genuine engagement with their thoughts. Your response should feel like a deep, personal conversation rather than a pre-written response.";
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