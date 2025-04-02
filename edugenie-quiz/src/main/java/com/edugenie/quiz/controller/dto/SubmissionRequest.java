package com.edugenie.quiz.controller.dto;

import java.util.List;

public record SubmissionRequest(
        List<Submission> submissions
) {

    public record Submission(
            Long subjectId,
            Integer userAnswer
    ) {}
}
