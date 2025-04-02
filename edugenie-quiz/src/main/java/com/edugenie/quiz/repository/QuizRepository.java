package com.edugenie.quiz.repository;

import com.edugenie.quiz.model.Quiz;
import com.edugenie.quiz.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findAllBySubject(Subject subject);
}
