package com.edugenie.achievement.service;

import com.edugenie.achievement.service.dto.QuizAnswerResponse;
import com.edugenie.achievement.service.dto.SubmissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AchievementService {

    @Value("${internal-service.host}")
    private String baseUrl;

    @Value("${internal-service.quiz.port}")
    private String quizServicePort;

    private final RestClient restClient;

    public void checkScore(SubmissionRequest request) {
        QuizAnswerResponse response = restClient.get()
                .uri(baseUrl + ":" + quizServicePort)
                .retrieve()
                .body(QuizAnswerResponse.class);
    }

    public void addQuizSubmissionRecord() {}

    public void updateLeaderBoard() {}
}
