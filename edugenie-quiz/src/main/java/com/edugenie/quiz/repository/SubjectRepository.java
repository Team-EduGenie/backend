package com.edugenie.quiz.repository;

import com.edugenie.quiz.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByGroupId(Long groupId);
    Page<Subject> findByGroupId(Long groupId, Pageable pageable);
}
