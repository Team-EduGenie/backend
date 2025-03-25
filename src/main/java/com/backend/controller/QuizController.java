package com.backend.controller;

import com.backend.model.Quiz;
import com.backend.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quizzes")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class QuizController {
    
    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<Quiz>> getQuizzes(
            @RequestParam Long studentId,
            @RequestParam Long unitId) {
        try {
            List<Quiz> quizzes = quizService.getQuizzesByUnitAndDifficulty(studentId, unitId);
            return ResponseEntity.ok(quizzes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<Void> submitQuiz(@RequestBody Map<String, Object> request) {
        try {
            Long studentId = Long.parseLong(request.get("studentId").toString());
            Long unitId = Long.parseLong(request.get("unitId").toString());
            int totalQuestions = Integer.parseInt(request.get("totalQuestions").toString());
            int correctAnswers = Integer.parseInt(request.get("correctAnswers").toString());
            
            quizService.submitQuizResult(studentId, unitId, totalQuestions, correctAnswers);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
} 