package com.edugenie.repository;

import com.edugenie.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByQuizSet_QuizsetId(Long quizsetId);

    @Query(value = """
        SELECT q.* FROM quiz q
        JOIN quizset qs ON q.quizset_id = qs.quizset_id
        WHERE qs.unit_id = :unitId
        AND qs.quiz_diff = :difficulty
        """, nativeQuery = true)
    List<Quiz> findByUnitIdAndDifficulty(
        @Param("unitId") Long unitId,
        @Param("difficulty") int difficulty
    );

    @Query(value = """
        SELECT qs.quiz_diff, COUNT(DISTINCT qs.quizset_id) 
        FROM quizset qs 
        WHERE qs.unit_id = :unitId
        GROUP BY qs.quiz_diff 
        ORDER BY qs.quiz_diff
        """, nativeQuery = true)
    List<Object[]> findQuizSetCountsByDifficulty(@Param("unitId") Long unitId);
} 