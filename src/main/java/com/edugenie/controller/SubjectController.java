package com.edugenie.controller;

import com.edugenie.model.Subject;
import com.edugenie.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getActiveSubjects();
    }
} 