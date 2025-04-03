package com.edugenie.ai.service;

import com.edugenie.ai.service.dto.QuizGenerateRequest;
import com.edugenie.ai.service.dto.QuizSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizGenerateService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    public void generateQuizzesFromPDF(QuizGenerateRequest request) {
        try {
            StringBuilder contents = new StringBuilder();
//            for (String url : request.attachmentUrls()) {
//                contents.append(extractTextFromPDF(url)).append("\n");
//            }
            contents.append(extractTextFromPDF("test")).append("\n");
            generateQuizzesFromText(contents.toString(), request.subjectName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate quiz from PDF.", e);
        }
    }

    private String extractTextFromPDF(String pdfPath) throws IOException {
        // TODO: blob storage에서 파일 추출
//        File file = Paths.get("../pdf/test.pdf").toFile();
//        System.out.println("file size: " + file.length());

        ClassLoader classLoader = QuizGenerateService.class.getClassLoader();
        URL resource = classLoader.getResource("pdf/test.pdf");
        if (resource == null) {
            throw new IllegalArgumentException("파일을 찾을 수 없습니다.");
        }

        File file = new File(resource.getFile());
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private void generateQuizzesFromText(String content, String subjectName) throws JsonProcessingException {
        int chunkSize = 2000;
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < content.length(); i += chunkSize) {
            chunks.add(content.substring(i, Math.min(i + chunkSize, content.length())));
        }
        for (String chunk : chunks) {
            generateQuizzesForChunk(chunk, subjectName);
        }
    }

    private void generateQuizzesForChunk(String chunk, String subjectName) throws JsonProcessingException {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", subjectName +
                """
                과 관련한 pdf 자료를 텍스트로 분석하여 교육 목표와 성취 기준이 무엇이 될 지 스스로 파악하고, 
                핵심 개념, 필수 지식, 수행해야 할 기능/역량을 스스로 파악해야 돼. 
                그리고 각 교육 목표와 성취 기준에 맞는 4지선다형 객관식 10문제를 만들어줘
                문제는 각 챕터별로 개념 이해와 문제 해결 능력, 실무 역량을 모두 기를 수 있도록 해야하고, 
                해당 문제를 학습하면서 특정 분야의 전문가로 성장해나갈 것을 목표로 해야돼  
                        """ +
                "각 문항은 아래 JSON 형식으로 출력해줘\n" +
                "{\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"difficulty\": \"easy/medium/hard\",\n" +
                "      \"question\": \"학생을 대상으로 한 교육과정 기반 문항 내용\",\n" +
                "      \"options\": [\"option1\", \"option2\", \"option3\", \"option4\"],\n" +
                "      \"correctAnswer\": \"정답 번호(1-4)\",\n" +
                "      \"explanation\": \"해당 문제의 보기별로 정답/오답인 이유를 설명\"\n" +
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

            String jsonPattern = "```json\\s*(\\{.*?\\})\\s*```";
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(jsonPattern, java.util.regex.Pattern.DOTALL);
            java.util.regex.Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                QuizSet quizSet = objectMapper.readValue(matcher.group(1), QuizSet.class);
                streamBridge.send("quizSet-out-0", quizSet);
            }
        }
    }
}
