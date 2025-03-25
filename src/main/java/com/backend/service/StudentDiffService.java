package com.backend.service;

import com.backend.model.StudentDiff;
import com.backend.repository.StudentDiffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentDiffService {
    
    @Autowired
    private StudentDiffRepository studentDiffRepository;

    @Transactional(readOnly = true)
    public int getStudentDiff(Long studentId, Long unitId) {
        StudentDiff studentDiff = studentDiffRepository.findByStudentIdAndUnitId(studentId, unitId);
        return studentDiff != null ? studentDiff.getSetDiff() : 3; // 기본값 3
    }

    @Transactional
    public void updateStudentDiff(Long studentId, Long unitId, int newDiff) {
        StudentDiff studentDiff = studentDiffRepository.findByStudentIdAndUnitId(studentId, unitId);
        if (studentDiff == null) {
            studentDiff = new StudentDiff();
            studentDiff.setStudentId(studentId);
            studentDiff.setUnitId(unitId);
            studentDiff.setScore(0);
        }
        studentDiff.setSetDiff(newDiff);
        studentDiffRepository.save(studentDiff);
    }

    @Transactional
    public void incrementScore(Long studentId, Long unitId) {
        StudentDiff studentDiff = studentDiffRepository.findByStudentIdAndUnitId(studentId, unitId);
        if (studentDiff == null) {
            studentDiff = new StudentDiff();
            studentDiff.setStudentId(studentId);
            studentDiff.setUnitId(unitId);
            studentDiff.setScore(0);
        }
        studentDiff.setScore(studentDiff.getScore() + 1);
        studentDiffRepository.save(studentDiff);
    }

    @Transactional(readOnly = true)
    public int getScore(Long studentId, Long unitId) {
        StudentDiff studentDiff = studentDiffRepository.findByStudentIdAndUnitId(studentId, unitId);
        return studentDiff != null ? studentDiff.getScore() : 0;
    }

    @Transactional
    public void resetScore(Long studentId, Long unitId) {
        StudentDiff studentDiff = studentDiffRepository.findByStudentIdAndUnitId(studentId, unitId);
        if (studentDiff == null) {
            studentDiff = new StudentDiff();
            studentDiff.setStudentId(studentId);
            studentDiff.setUnitId(unitId);
            studentDiff.setSetDiff(3); // 기본 난이도 3
        }
        studentDiff.setScore(0);
        studentDiffRepository.save(studentDiff);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getScoresByTeacherId(Long teacherId) {
        List<Object[]> results = studentDiffRepository.findScoresByTeacherId(teacherId);
        
        return results.stream().map(result -> {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("studentName", (String) result[0]);
            scoreMap.put("subjectName", (String) result[1]);
            scoreMap.put("unitName", (String) result[2]);
            scoreMap.put("score", ((Number) result[3]).longValue());
            scoreMap.put("totalQuestions", ((Number) result[4]).longValue());
            scoreMap.put("difficulty", ((Number) result[5]).intValue());
            
            double percentage = ((Number) result[4]).longValue() > 0 
                ? (((Number) result[3]).longValue() * 100.0) / ((Number) result[4]).longValue()
                : 0.0;
            
            scoreMap.put("percentage", percentage);
            return scoreMap;
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getScoresByTeacherIdAndSubjectId(Long teacherId, Long subjectId) {
        List<Object[]> results = studentDiffRepository.findScoresByTeacherIdAndSubjectId(teacherId, subjectId);
        
        return results.stream().map(result -> {
            Map<String, Object> scoreMap = new HashMap<>();
            scoreMap.put("studentName", (String) result[0]);
            scoreMap.put("unitName", (String) result[1]);
            scoreMap.put("score", ((Number) result[2]).longValue());
            scoreMap.put("totalQuestions", ((Number) result[3]).longValue());
            scoreMap.put("difficulty", ((Number) result[4]).intValue());
            
            double percentage = ((Number) result[3]).longValue() > 0 
                ? (((Number) result[2]).longValue() * 100.0) / ((Number) result[3]).longValue()
                : 0.0;
            
            scoreMap.put("percentage", percentage);
            return scoreMap;
        }).collect(Collectors.toList());
    }
} 