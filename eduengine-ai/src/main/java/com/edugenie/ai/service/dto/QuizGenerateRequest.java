package com.edugenie.ai.service.dto;

import java.util.List;

public record QuizGenerateRequest(
        Long subjectId,
        String subjectName,
        List<String> attachmentUrls
) {
}
