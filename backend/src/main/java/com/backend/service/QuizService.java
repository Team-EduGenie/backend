package com.backend.service;

import com.backend.model.Quiz;
import com.backend.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final StudentDiffService studentDiffService;

    @Transactional(readOnly = true)
    public List<Quiz> getQuizzesByUnitAndDifficulty(Long studentId, Long unitId) {
        // 현재 학생의 난이도 가져오기
        int currentDiff = studentDiffService.getStudentDiff(studentId, unitId);
        
        // 해당 단원의 현재 난이도에 맞는 퀴즈 가져오기
        return quizRepository.findByUnitIdAndDifficulty(unitId, currentDiff);
    }

    @Transactional
    public void submitQuizResult(Long studentId, Long unitId, int totalQuestions, int correctAnswers) {
        // 점수 업데이트
        if (correctAnswers > 0) {
            studentDiffService.incrementScore(studentId, unitId);
        }
        
        // 난이도 업데이트
        double successRate = (double) correctAnswers / totalQuestions;
        int currentDiff = studentDiffService.getStudentDiff(studentId, unitId);
        
        int newDiff = calculateNewDifficulty(currentDiff, successRate);
        
        if (newDiff != currentDiff) {
            studentDiffService.updateStudentDiff(studentId, unitId, newDiff);
        }
    }

    private int calculateNewDifficulty(int currentDiff, double successRate) {
        if (successRate >= 0.5) {
            return Math.min(currentDiff + 1, 5);  // 최대 5
        } else {
            return Math.max(currentDiff - 1, 1);  // 최소 1
        }
    }
} 