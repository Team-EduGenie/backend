package com.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_diff")
@IdClass(StudentDiffId.class)
public class StudentDiff {
    @Id
    @Column(name = "student_id")
    private Long studentId;

    @Id
    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "set_diff")
    private Integer setDiff;
}

