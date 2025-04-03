package com.edugenie.quiz.service;

import com.edugenie.quiz.controller.dto.QuizGenerateRequest;
import com.edugenie.quiz.model.Quiz;
import com.edugenie.quiz.model.Subject;
import com.edugenie.quiz.repository.QuizRepository;
import com.edugenie.quiz.repository.SubjectRepository;
import com.edugenie.quiz.service.dto.GenerateQuizRequest;
import com.edugenie.quiz.service.dto.QuizAnswerResult;
import com.edugenie.quiz.service.dto.QuizResult;
import com.edugenie.quiz.service.dto.QuizSet;
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
    public void generateQuiz(QuizGenerateRequest quizGenerateRequest) {
        Subject subject = subjectRepository.findById(quizGenerateRequest.subjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        GenerateQuizRequest generateQuizRequest = GenerateQuizRequest.of(quizGenerateRequest, subject.getTitle());
        streamBridge.send("quizGenerator-out-0", generateQuizRequest);
    }

    @Transactional
    public void addQuizzes(QuizSet quizSet) {
        // TODO: subjectId를 quizSet에서 가져오도록 수정
//        Subject subject = subjectRepository.findById(quizSet.subjectId())
//                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        Subject subject = subjectRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        List<Quiz> quizzes = quizSet.questions().stream()
                .map(question -> QuizSet.toEntity(subject, question)).toList();
        quizRepository.saveAll(quizzes);
    }

    @Transactional(readOnly = true)
    public List<QuizResult> findQuizzes(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        List<Quiz> quizzes = quizRepository.findAllBySubject(subject);
        return quizzes.stream().map(QuizResult::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<QuizAnswerResult> findQuizAnswers(List<Long> quizIds) {
        List<Quiz> quizzes = quizRepository.findAllById(quizIds);
        return quizzes.stream().map(QuizAnswerResult::fromEntity).toList();
    }
}
