package com.edugenie.service;

import com.edugenie.model.Subject;
import com.edugenie.repository.SubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional(readOnly = true)
    public List<Subject> getActiveSubjects() {
        return subjectRepository.findAll();
    }
} 