package com.edugenie.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDiffId implements Serializable {
    private Long studentId;
    private Long unitId;
} 