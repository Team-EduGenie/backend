package com.edugenie.controller;

import com.edugenie.service.QuizGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Map;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/quiz-generator")
@RequiredArgsConstructor
public class QuizGeneratorController {

    private final QuizGeneratorService quizGeneratorService;
    private static final String PDF_DIRECTORY = "C:/Users/wordy/OneDrive/Desktop/backend/src/main/resources/test-pdfs/";

    @PostMapping("/generate-from-pdf")
    public ResponseEntity<?> generateQuizzesFromPDF(@RequestBody Map<String, Object> request) {
        try {
            String subjectName = (String) request.get("subjectName");
            String attachmentName = (String) request.get("attachmentNames");
            
            if (attachmentName == null || attachmentName.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "첨부 파일이 없습니다."
                ));
            }
            
            File pdfFile = new File(PDF_DIRECTORY + attachmentName);
            
            if (!pdfFile.exists()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "파일을 찾을 수 없습니다: " + attachmentName
                ));
            }

            quizGeneratorService.generateQuizzesFromPDF(pdfFile.getAbsolutePath(), subjectName);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "퀴즈가 성공적으로 생성되었습니다."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                    "success", false,
                    "message", "퀴즈 생성 중 오류가 발생했습니다: " + e.getMessage()
                ));
        }
    }
} 