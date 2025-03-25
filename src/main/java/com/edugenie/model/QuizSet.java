package com.edugenie.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "quizset")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class QuizSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quizset_id")
    private Long quizsetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    @JsonIgnoreProperties("quizSets")
    private Unit unit;

    @Column(name = "quiz_name")
    private String quizName;

    @Column(name = "quiz_diff")
    private Integer quizDiff;

    @OneToMany(mappedBy = "quizSet", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("quizSet")
    private List<Quiz> quizzes;
} 