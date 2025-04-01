package com.edugenie.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class QuizGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(QuizGeneratorService.class);

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;

    public void generateQuizzesFromPDF(String pdfPath, String subjectName) {
        try {
            String content = extractTextFromPDF(pdfPath);
            generateQuizzesFromText(content, subjectName);
        } catch (Exception e) {
            logger.error("PDF based quiz generation error: ", e);
            throw new RuntimeException("Failed to generate quiz from PDF.", e);
        }
    }

    private String extractTextFromPDF(String pdfPath) throws IOException {
        File file = new File(pdfPath);
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private void generateQuizzesFromText(String content, String subjectName) {
        // 텍스트를 2000자 단위로 나누기
        int chunkSize = 2000;
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < content.length(); i += chunkSize) {
            chunks.add(content.substring(i, Math.min(i + chunkSize, content.length())));
        }

        // 각 청크별로 퀴즈 생성
        for (String chunk : chunks) {
            generateQuizzesForChunk(chunk, subjectName);
        }
    }

    private void generateQuizzesForChunk(String chunk, String subjectName) {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "이 PDF는 '" + subjectName + "' 과목/시험을 위한 교육과정 문서입니다.\n\n" +
                "이 문서를 참고하여 **학생들이 배워야 할 내용과 성취해야 할 역량**을 기반으로 다음 조건에 맞는 5지선다형 문제를 **정확히 10개**만 만들어주세요.\n\n" +
                "문제는 단순한 암기 테스트가 아니라, 실제로 학생들이 배운 내용을 바탕으로 사고력과 이해도를 평가할 수 있어야 합니다.\n" +
                "각 문항은 다음 기준 중 하나를 반영해 구성해주세요:\n" +
                "1. 성취기준에서 요구하는 지식, 기능, 태도 중 하나를 평가하는 문항\n" +
                "2. 특정 학년 또는 과목에서 배우는 핵심 개념의 적용을 요구하는 문항\n" +
                "3. 실제 수업에서 사용할 수 있도록 구성된 상황형 문항 (예: 사례 제시 → 선택)\n\n" +
                "각 문항은 아래 JSON 형식으로 출력해주세요:\n" +
                "{\n" +
                "  \"문제들\": [\n" +
                "    {\n" +
                "      \"난이도\": \"쉬움/중간/어려움\",\n" +
                "      \"문제\": \"학생을 대상으로 한 교육과정 기반 문항 내용\",\n" +
                "      \"보기\": [\"보기1\", \"보기2\", \"보기3\", \"보기4\", \"보기5\"],\n" +
                "      \"정답\": \"정답 번호(1-5)\",\n" +
                "      \"관련 성취기준\": \"해당되는 성취기준 혹은 교육목표 요약\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n\n" +
                "PDF에서 발췌한 텍스트:\n" + chunk);


        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4-1106-preview");
        requestBody.put("messages", List.of(message));
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        if (!choices.isEmpty()) {
            Map<String, Object> choice = choices.get(0);
            Map<String, Object> messageResponse = (Map<String, Object>) choice.get("message");
            String text = (String) messageResponse.get("content");
            logger.info("생성된 퀴즈: {}", text);
        }
    }
}