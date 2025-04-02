package com.edugenie.achievement.service;

import com.edugenie.achievement.service.dto.SubmissionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final AchievementService achievementService;

    @Bean
    public Consumer<SubmissionRequest> submissionChannel() {
        return achievementService::checkScore;
    }
}
