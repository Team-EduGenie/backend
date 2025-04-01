package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.model.Quiz;
import lombok.Builder;

@Builder
public record QuizResult(
        Long id,
        String question,
        String options
) {

    public static QuizResult fromEntity(Quiz quiz) {
        return QuizResult.builder()
                .id(quiz.getId())
                .question(quiz.getQuestion())
                .build();
    }
}
