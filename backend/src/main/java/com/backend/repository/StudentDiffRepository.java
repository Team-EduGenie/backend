package com.backend.repository;

import com.backend.model.StudentDiff;
import com.backend.model.StudentDiffId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDiffRepository extends JpaRepository<StudentDiff, StudentDiffId> {
    StudentDiff findByStudentIdAndUnitId(Long studentId, Long unitId);

    @Query(value = """
        SELECT s.student_name, sub.subject_name, u.unit_name, 
               sd.score, COUNT(DISTINCT q.quiz_id) as total_questions,
               sd.set_diff as difficulty
        FROM student s
        JOIN student_diff sd ON s.student_id = sd.student_id
        JOIN unit u ON sd.unit_id = u.unit_id
        JOIN subject sub ON u.subject_id = sub.subject_id
        LEFT JOIN quizset qs ON qs.unit_id = u.unit_id
        LEFT JOIN quiz q ON q.quizset_id = qs.quizset_id
        WHERE s.teacher_id = :teacherId
        GROUP BY s.student_name, sub.subject_name, u.unit_name, sd.score, sd.set_diff
        ORDER BY s.student_name, sub.subject_name, u.unit_name
        """, nativeQuery = true)
    List<Object[]> findScoresByTeacherId(@Param("teacherId") Long teacherId);

    @Query(value = """
        SELECT s.student_name, u.unit_name, 
               sd.score, COUNT(DISTINCT q.quiz_id) as total_questions,
               sd.set_diff as difficulty
        FROM student s
        JOIN student_diff sd ON s.student_id = sd.student_id
        JOIN unit u ON sd.unit_id = u.unit_id
        JOIN subject sub ON u.subject_id = sub.subject_id
        LEFT JOIN quizset qs ON qs.unit_id = u.unit_id
        LEFT JOIN quiz q ON q.quizset_id = qs.quizset_id
        WHERE s.teacher_id = :teacherId AND sub.subject_id = :subjectId
        GROUP BY s.student_name, u.unit_name, sd.score, sd.set_diff
        ORDER BY s.student_name, u.unit_name
        """, nativeQuery = true)
    List<Object[]> findScoresByTeacherIdAndSubjectId(
        @Param("teacherId") Long teacherId,
        @Param("subjectId") Long subjectId);
} 