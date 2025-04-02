package com.edugenie.quiz.service;

import com.edugenie.quiz.controller.dto.SubjectAddRequest;
import com.edugenie.quiz.model.Subject;
import com.edugenie.quiz.repository.SubjectRepository;
import com.edugenie.quiz.service.dto.SubjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Transactional
    public void addSubject(SubjectAddRequest request) {
        // TODO: Leader가 추가한 subject인지 확인하는 로직 추가
        subjectRepository.save(request.toEntity());
    }

    @Transactional(readOnly = true)
    public List<SubjectResult> findSubjects(Long groupId) {
        List<Subject> subjects = subjectRepository.findByGroupId(groupId);
        return subjects.stream().map(SubjectResult::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<SubjectResult> findSubjects(Long groupId, Pageable pageable) {
        List<Subject> subjects = subjectRepository.findByGroupId(groupId, pageable).getContent();
        return subjects.stream().map(SubjectResult::fromEntity).toList();
    }
}
