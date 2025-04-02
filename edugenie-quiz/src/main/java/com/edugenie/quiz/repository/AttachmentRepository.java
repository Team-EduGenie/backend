package com.edugenie.quiz.repository;

import com.edugenie.quiz.model.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Page<Attachment> findBySubjectId(Long subjectId, Pageable pageable);
}
