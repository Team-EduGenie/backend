package com.edugenie.repository;

import com.edugenie.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByTeacher_TeacherId(Long teacherId);
    Optional<Student> findByStudentName(String studentName);
}
