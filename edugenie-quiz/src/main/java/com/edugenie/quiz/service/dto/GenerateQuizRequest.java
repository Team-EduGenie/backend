package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.controller.dto.QuizGenerateRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record GenerateQuizRequest(
        Long subjectId,
        String subjectName,
        List<String> attachmentUrls
) {

    public static GenerateQuizRequest of(QuizGenerateRequest request, String subjectName) {
        return GenerateQuizRequest.builder()
                .subjectId(request.subjectId())
                .subjectName(subjectName)
                .attachmentUrls(request.attachmentUrls())
                .build();
    }
}
