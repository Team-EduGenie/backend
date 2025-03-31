package com.edugenie.dto;

import lombok.Data;

@Data
public class FeedbackRequestDto {
    private Long studentId;
    private String question;
    private String correctAnswer;
    private String userAnswer;
}