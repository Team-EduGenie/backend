package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.model.Quiz;
import lombok.Builder;

@Builder
public record QuizAnswerResult(
        Long quizId,
        Integer answer
) {

    public static QuizAnswerResult fromEntity(Quiz quiz) {
        return QuizAnswerResult.builder()
                .quizId(quiz.getId())
                .answer(quiz.getAnswer())
                .build();

    }
}
