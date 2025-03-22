package com.backend.service;

import com.backend.dto.QuizDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomQuizService {

    @Value("${openai.api.key}")
    private String apiKey;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    public QuizDto generateQuiz(String type) {
        OpenAiService service = new OpenAiService(apiKey);
        
        String prompt;
        if ("cooking".equals(type)) {
            prompt = "당신은 요리사 선생님입니다. 8살 아이들에게 요리 과정을 재미있게 익히도록, 다음과 같은 형식으로 퀴즈를 만들어주세요:\n" +
                    "1. 재료 맞추기 문제 (예: '나는 달콤하고 바삭바삭해요. 밀가루, 설탕, 버터로 만들어져요. 나는 무엇일까요?' - 답: 쿠키)\n" +
                    "2. 요리 도구 맞추기 (예: '나는 음식을 자를 때 사용해요. 칼처럼 날카롭지만 더 작고 안전해요. 나는 무엇일까요?' - 답: 가위)\n" +
                    "3. 요리 과정 맞추기 (예: '계란을 삶을 때 가장 먼저 해야 하는 일은 무엇일까요?' - 답: 물을 끓이기)\n" +
                    "다음 JSON 형식으로 응답해주세요: {\"question\": \"질문\", \"answer\": \"답\"}\n" +
                    "답은 한 단어로 해주세요.";
        } else {
            prompt = "당신은 재미있는 수수께끼를 만드는 전문가입니다. 다음 조건에 맞는 수수께끼를 만들어주세요:\n" +
                    "1. 동물, 과일, 자연물, 일상용품 등 친숙한 사물을 주제로 합니다.\n" +
                    "2. 사물의 특징을 재미있게 설명합니다.\n" +
                    "3. 답은 반드시 한 단어로 되어있어야 합니다.\n\n" +
                    "다음 JSON 형식으로 응답해주세요: {\"question\": \"질문\", \"answer\": \"답\"}\n\n" +
                    "예시:\n" +
                    "{\"question\": \"나는 동그란 얼굴을 가졌어요. 밤이 되면 하늘에서 반짝반짝 빛나고, 바다의 물을 움직일 수도 있어요. 나는 무엇일까요?\", \"answer\": \"달\"}\n" +
                    "{\"question\": \"나는 키가 아주 크고 목이 길어요. 노란 반점이 있는 코트를 입었죠. 나뭇잎을 아주 맛있게 먹어요. 나는 무엇일까요?\", \"answer\": \"기린\"}";
        }

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", prompt));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();

        try {
            String response = service.createChatCompletion(completionRequest)
                    .getChoices().get(0).getMessage().getContent();

            // JSON 파싱
            JsonNode jsonNode = objectMapper.readTree(response);
            String question = jsonNode.get("question").asText();
            String answer = jsonNode.get("answer").asText();

            return new QuizDto(question, answer);
        } catch (Exception e) {
            e.printStackTrace();
            if ("cooking".equals(type)) {
                return new QuizDto(
                    "나는 달콤하고 바삭바삭해요. 밀가루, 설탕, 버터로 만들어져요. 나는 무엇일까요?",
                    "쿠키"
                );
            } else {
                return new QuizDto(
                    "나는 둥글고 노란색이며, 바나나처럼 생겼습니다.",
                    "바나나"
                );
            }
        }
    }

    public QuizDto generateQuizWithPrompt(String customPrompt) {
        OpenAiService service = new OpenAiService(apiKey);
        
        String prompt = customPrompt + "\n\n다음 JSON 형식으로 응답해주세요: {\"question\": \"질문\", \"answer\": \"답\"}\n" +
                "답은 반드시 한 단어로 해주세요.";

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage("system", prompt));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();

        try {
            String response = service.createChatCompletion(completionRequest)
                    .getChoices().get(0).getMessage().getContent();

            // JSON 파싱
            JsonNode jsonNode = objectMapper.readTree(response);
            String question = jsonNode.get("question").asText();
            String answer = jsonNode.get("answer").asText();

            return new QuizDto(question, answer);
        } catch (Exception e) {
            e.printStackTrace();
            return new QuizDto(
                "나는 둥글고 노란색이며, 바나나처럼 생겼습니다.",
                "바나나"
            );
        }
    }
} 