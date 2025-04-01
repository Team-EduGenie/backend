package com.edugenie.quiz.controller;

import com.edugenie.quiz.controller.dto.QuizAddRequest;
import com.edugenie.quiz.service.QuizService;
import com.edugenie.quiz.service.dto.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizResult>> quizList(@RequestParam Long subjectId) {
        List<QuizResult> results = quizService.findQuizzes(subjectId);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<Void> quizAdd(@RequestBody QuizAddRequest request) {
        quizService.generateQuiz(request);
        return ResponseEntity.ok().build();
    }
}
