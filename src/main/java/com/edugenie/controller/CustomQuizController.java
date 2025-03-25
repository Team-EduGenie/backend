package com.edugenie.controller;

import com.edugenie.dto.QuizDto;
import com.edugenie.service.CustomQuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/custom-quiz")
@RequiredArgsConstructor
public class CustomQuizController {

    private final CustomQuizService customQuizService;

    @GetMapping("/generate")
    public ResponseEntity<QuizDto> generateQuiz(@RequestParam(defaultValue = "riddle") String type) {
        QuizDto quiz = customQuizService.generateQuiz(type);
        return ResponseEntity.ok(quiz);
    }

    @PostMapping("/generate-with-prompt")
    public ResponseEntity<QuizDto> generateQuizWithPrompt(@RequestBody String prompt) {
        QuizDto quiz = customQuizService.generateQuizWithPrompt(prompt);
        return ResponseEntity.ok(quiz);
    }
} 