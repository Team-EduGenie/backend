package com.edugenie.controller;

import com.edugenie.service.QuizGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quiz-generator")
@RequiredArgsConstructor
public class QuizGeneratorController {

    private final QuizGeneratorService quizGeneratorService;

    @PostMapping("/initialize")
    public ResponseEntity<String> initializeQuizzes() {
        try {
            quizGeneratorService.initializeSubjectsAndUnits();
            return ResponseEntity.ok("문제 초기화 및 생성이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("문제 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
} 