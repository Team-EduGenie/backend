package com.edugenie.quiz.service;

import com.edugenie.quiz.model.Attachment;
import com.edugenie.quiz.repository.AttachmentRepository;
import com.edugenie.quiz.service.dto.AttachmentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public void uploadAttachment() {
        // azure blob 업로드
    }

    public List<AttachmentResult> findAttachments(Long subjectId, Pageable pageable) {
        List<Attachment> attachments = attachmentRepository.findBySubjectId(subjectId, pageable).getContent();
        return attachments.stream().map(AttachmentResult::fromEntity).toList();
    }
}
