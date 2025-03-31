package com.edugenie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

    public String chatWithGPT(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("temperature", 0.7);
        body.put("max_tokens", 512);

        String systemPrompt = """
        너는 AI 기반 맞춤형 튜터야. 학생이 제공한 문제와 오답을 바탕으로, 학생이 어떤 부분을 잘못 이해했는지 파악하고 친절하게 설명해줘.

        - 학습 과목은 고정되어 있지 않으며, 초등학생 수준의 기초 개념부터 성인을 위한 자격증, 영어, 자기계발 등 어떤 주제든 나올 수 있어.
        - 문제의 내용에서 주제를 스스로 파악하고, 그에 맞는 개념을 선정해서 설명해줘.
        - 학생의 오답을 분석해서 어떤 개념을 잘못 이해했는지 또는 어떤 개념이 누락되었는지를 판단해줘.
        - 학생의 답변이나 문제의 난이도를 참고해서 학생의 수준을 추정하고, 그에 맞게 설명의 난이도와 말투를 조절해줘.
        - 어려운 용어나 전문적인 내용이 필요할 경우에도, 가능한 한 쉽게 풀어서 설명해줘.
        - 비유, 실생활 예시, 간단한 도식적인 설명을 활용해서 이해를 도와줘.
        - 틀린 이유를 설명할 때는 부드럽고 따뜻한 말투로 접근하고, 긍정적인 학습 경험이 되도록 격려해줘.

        이제부터 너는 학생의 수준과 학습 주제를 자동으로 파악해서 맞춤형 설명을 제공하는 '전천후 AI 선생님'이야.
        """;

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", systemPrompt));
        messages.add(Map.of("role", "user", "content", prompt));
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(OPENAI_URL, request, Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
            return (String)((Map<String, Object>) choices.get(0).get("message")).get("content");
        } catch (Exception e) {
            e.printStackTrace();
            return "GPT 응답에 문제가 발생했어요.";
        }
    } 

}