package com.edugenie.ai.service.dto;

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
}

