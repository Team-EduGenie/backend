package com.edugenie.achievement.service.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record QuizAnswerResponse(
        List<QuizAnswer> quizAnswers
) {

    public record QuizAnswer(
            Long quizId,
            Integer answer
    ) {}
}
