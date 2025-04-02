package com.edugenie.achievement.service.dto;

import java.util.List;

public record SubmissionRequest(
        Long subjectId,
        List<Submission> submissions
) {

    public record Submission(
            Long quizId,
            Integer userAnswer
    ) {}
}
