package com.edugenie.quiz.controller;

import com.edugenie.quiz.controller.dto.SubmissionRequest;
import com.edugenie.quiz.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/submissions")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/{subjectId}")
    public ResponseEntity<Void> submit(@PathVariable Long subjectId, @RequestBody SubmissionRequest request) {
        submissionService.sendSubmission(subjectId, request);
        return ResponseEntity.ok().build();
    }
 }
