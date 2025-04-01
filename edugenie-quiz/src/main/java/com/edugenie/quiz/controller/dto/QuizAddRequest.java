package com.edugenie.quiz.controller.dto;

import java.util.List;

public record QuizAddRequest(
        Long subjectId,
        List<String> attachmentUrls
) {
}
