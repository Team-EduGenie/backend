package com.edugenie.controller;

import com.edugenie.service.StudentDiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/student-diff")
@CrossOrigin(origins = "http://localhost:5173")
public class StudentDiffController {

    @Autowired
    private StudentDiffService studentDiffService;

    @PostMapping("/increment-score")
    public ResponseEntity<Void> incrementScore(@RequestBody Map<String, Long> request) {
        try {
            Long studentId = request.get("studentId");
            Long unitId = request.get("unitId");
            
            if (studentId == null || unitId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            studentDiffService.incrementScore(studentId, unitId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/reset-score")
    public ResponseEntity<Void> resetScore(@RequestBody Map<String, Long> request) {
        try {
            Long studentId = request.get("studentId");
            Long unitId = request.get("unitId");
            
            if (studentId == null || unitId == null) {
                return ResponseEntity.badRequest().build();
            }
            
            studentDiffService.resetScore(studentId, unitId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/score")
    public ResponseEntity<Map<String, Integer>> getScore(
            @RequestParam Long studentId,
            @RequestParam Long unitId) {
        try {
            int score = studentDiffService.getScore(studentId, unitId);
            int difficulty = studentDiffService.getStudentDiff(studentId, unitId);
            Map<String, Integer> response = new HashMap<>();
            response.put("score", score);
            response.put("difficulty", difficulty);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Map<String, Object>>> getScoresByTeacher(@PathVariable Long teacherId) {
        List<Map<String, Object>> scores = studentDiffService.getScoresByTeacherId(teacherId);
        return ResponseEntity.ok(scores);
    }

    @GetMapping("/teacher/{teacherId}/subject/{subjectId}")
    public ResponseEntity<List<Map<String, Object>>> getScoresByTeacherAndSubject(
            @PathVariable Long teacherId,
            @PathVariable Long subjectId) {
        List<Map<String, Object>> scores = studentDiffService.getScoresByTeacherIdAndSubjectId(teacherId, subjectId);
        return ResponseEntity.ok(scores);
    }
} 