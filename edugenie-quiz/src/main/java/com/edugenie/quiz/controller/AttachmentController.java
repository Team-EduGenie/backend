package com.edugenie.quiz.controller;

import com.edugenie.quiz.service.AttachmentService;
import com.edugenie.quiz.service.dto.AttachmentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping
    public ResponseEntity<List<AttachmentResult>> attachmentList(@RequestParam Long subjectId, @PageableDefault(sort = "id") Pageable pageable) {
        List<AttachmentResult> attachments = attachmentService.findAttachments(subjectId, pageable);
        return ResponseEntity.ok(attachments);
    }

    // s3 업로드
    @PostMapping
    public ResponseEntity<Void> uploadAttachment() {
        return ResponseEntity.ok().build();
    }
}
