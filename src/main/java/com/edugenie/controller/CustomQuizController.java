package com.edugenie.controller;

import com.edugenie.dto.QuizDto;
import com.edugenie.service.CustomQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/custom-quiz")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomQuizController {

    private final CustomQuizService customQuizService;

    @Autowired
    public CustomQuizController(CustomQuizService customQuizService) {
        this.customQuizService = customQuizService;
    }

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