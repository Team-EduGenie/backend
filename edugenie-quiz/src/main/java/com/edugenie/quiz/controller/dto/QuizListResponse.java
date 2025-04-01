package com.edugenie.quiz.controller.dto;

import java.time.LocalDate;
import java.util.List;

public record QuizListResponse(
        Long subjectId,
        String subjectName,
        List<String> attachmentNames,
        LocalDate createdTime
) {
}
