package com.edugenie.quiz.controller;

import com.edugenie.quiz.controller.dto.SubjectAddRequest;
import com.edugenie.quiz.service.SubjectService;
import com.edugenie.quiz.service.dto.SubjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<SubjectResult>> subjectList(@RequestParam Long groupId, @PageableDefault(sort = "id") Pageable pageable) {
        List<SubjectResult> subjects = subjectService.findSubjects(groupId, pageable);
        return ResponseEntity.ok(subjects);
    }

    @PostMapping
    public ResponseEntity<Void> subjectAdd(@RequestBody SubjectAddRequest request) {
        subjectService.addSubject(request);
        return ResponseEntity.ok().build();
    }
}
