package com.backend.controller;

import com.backend.service.QuizGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-generator")
@CrossOrigin(origins = "http://localhost:5173")
public class QuizGeneratorController {

    @Autowired
    private QuizGeneratorService quizGeneratorService;

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