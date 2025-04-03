package com.edugenie.ai.service;

import com.edugenie.ai.service.dto.QuizGenerateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final QuizGenerateService quizGenerateService;

    @Bean
    public Consumer<QuizGenerateRequest> quizGenerator() {
        return quizGenerateService::generateQuizzesFromPDF;
    }
}
