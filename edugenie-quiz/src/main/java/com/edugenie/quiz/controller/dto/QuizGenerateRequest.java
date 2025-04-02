package com.edugenie.quiz.controller.dto;

import java.util.List;

public record QuizGenerateRequest(
        Long subjectId,
        List<String> attachmentUrls
) {
}
