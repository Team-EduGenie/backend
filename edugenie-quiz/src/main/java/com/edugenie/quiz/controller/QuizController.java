package com.edugenie.quiz.controller;

import com.edugenie.quiz.controller.dto.QuizGenerateRequest;
import com.edugenie.quiz.service.QuizService;
import com.edugenie.quiz.service.dto.QuizAnswerResult;
import com.edugenie.quiz.service.dto.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizResult>> quizList(@RequestParam Long subjectId) {
        List<QuizResult> results = quizService.findQuizzes(subjectId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/answer")
    public ResponseEntity<List<QuizAnswerResult>> quizAnswerList(@RequestParam List<Long> quizIds) {
        List<QuizAnswerResult> results = quizService.findQuizAnswers(quizIds);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<Void> quizGenerate(@RequestBody QuizGenerateRequest request) {
        quizService.generateQuiz(request);
        return ResponseEntity.ok().build();
    }
}
