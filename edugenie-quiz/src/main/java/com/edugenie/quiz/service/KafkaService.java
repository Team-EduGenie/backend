package com.edugenie.quiz.service;

import com.edugenie.quiz.service.dto.QuizSet;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final QuizService quizService;

    @Bean
    public Consumer<QuizSet> submissionChannel() {
        return quizService::addQuizzes;
    }
}
