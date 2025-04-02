package com.edugenie.quiz.service;

import com.edugenie.quiz.controller.dto.SubmissionRequest;
import com.edugenie.quiz.service.dto.CheckScoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final StreamBridge streamBridge;

    public void sendSubmission(Long subjectId, SubmissionRequest submissionRequest) {
        CheckScoreRequest checkScoreRequest = CheckScoreRequest.of(subjectId, submissionRequest);
        streamBridge.send("submission-out-0", checkScoreRequest);
    }
}
