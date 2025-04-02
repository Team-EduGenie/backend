package com.edugenie.quiz.controller.dto;

import com.edugenie.quiz.model.Subject;

public record SubjectAddRequest(
        Long groupId,
        String title,
        String description
) {

    public Subject toEntity() {
        return Subject.builder()
                .groupId(groupId)
                .title(title)
                .description(description)
                .build();
    }
}
