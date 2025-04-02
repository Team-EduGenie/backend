package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.controller.dto.SubmissionRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record CheckScoreRequest(
        Long subjectId,
        List<SubmissionRequest.Submission> submissions
) {

    public static CheckScoreRequest of(Long subjectId, SubmissionRequest submissionRequest) {
        return CheckScoreRequest.builder()
                .subjectId(subjectId)
                .submissions(submissionRequest.submissions())
                .build();
    }
}
