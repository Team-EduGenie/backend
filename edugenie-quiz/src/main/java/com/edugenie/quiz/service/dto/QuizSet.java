package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.model.Quiz;
import com.edugenie.quiz.model.Subject;

import java.util.List;

public record QuizSet(
        Long subjectId,
        List<QuizQuestion> questions
) {
    public record QuizQuestion(
            String difficulty,
            String question,
            List<String> options,
            int correctAnswer,
            String explanation
    ) {

    }

    public static Quiz toEntity(Subject subject, QuizQuestion question) {
        return Quiz.builder()
                .question(question.question())
                .options(String.join(", ", question.options))
                .answer(question.correctAnswer())
                .explanation(question.explanation())
                .subject(subject)
                .build();
    }
}