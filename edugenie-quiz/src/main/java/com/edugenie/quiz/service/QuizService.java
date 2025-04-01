package com.edugenie.quiz.service;

import com.edugenie.quiz.controller.dto.QuizAddRequest;
import com.edugenie.quiz.model.Quiz;
import com.edugenie.quiz.model.Subject;
import com.edugenie.quiz.repository.QuizRepository;
import com.edugenie.quiz.repository.SubjectRepository;
import com.edugenie.quiz.service.dto.QuizResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final StreamBridge streamBridge;
    private final QuizRepository quizRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public void generateQuiz(QuizAddRequest request) {
        streamBridge.send("quizGenerator-out-0", request);
    }

    @Transactional(readOnly = true)
    public List<QuizResult> findQuizzes(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        List<Quiz> quizzes = quizRepository.findAllBySubject(subject);
        return quizzes.stream().map(QuizResult::fromEntity).toList();
    }
}
