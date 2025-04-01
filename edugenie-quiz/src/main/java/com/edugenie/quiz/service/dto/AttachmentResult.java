package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.model.Attachment;
import lombok.Builder;

@Builder
public record AttachmentResult(
        Long id,
        String name,
        String url
) {

    public static AttachmentResult fromEntity(Attachment attachment) {
        return AttachmentResult.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .url(attachment.getUrl())
                .build();
    }
}
