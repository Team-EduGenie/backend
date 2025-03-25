package com.edugenie.service;

import com.edugenie.model.Subject;
import com.edugenie.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Transactional(readOnly = true)
    public List<Subject> getActiveSubjects() {
        return subjectRepository.findAll();
    }
} 