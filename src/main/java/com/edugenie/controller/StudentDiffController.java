package com.edugenie.controller;

import com.edugenie.service.StudentDiffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/student-diff")
@RequiredArgsConstructor
public class StudentDiffController {

    private final StudentDiffService studentDiffService;

    @GetMapping("/score")
    public ResponseEntity<Map<String, Integer>> getScore(@RequestParam Long studentId, @RequestParam Long unitId) {
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
    public ResponseEntity<List<Map<String, Object>>> getScoresByTeacherAndSubject(@PathVariable Long teacherId, @PathVariable Long subjectId) {
        List<Map<String, Object>> scores = studentDiffService.getScoresByTeacherIdAndSubjectId(teacherId, subjectId);
        return ResponseEntity.ok(scores);
    }

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
}