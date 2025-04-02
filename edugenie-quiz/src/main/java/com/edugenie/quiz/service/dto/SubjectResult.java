package com.edugenie.quiz.service.dto;

import com.edugenie.quiz.model.Subject;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record SubjectResult(
        Long id,
        String subjectName,
        String description,
        // TODO
        Boolean isCreatedByLeader,
        LocalDate createdTime
) {

    public static SubjectResult fromEntity(Subject subject) {
        return SubjectResult.builder()
                .id(subject.getId())
                .subjectName(subject.getTitle())
                .description(subject.getDescription())
                .build();
    }
}
