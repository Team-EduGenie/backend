package com.edugenie.service;

import com.edugenie.model.Student;
import com.edugenie.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getStudentsByTeacherId(Long teacherId) {
        return studentRepository.findByTeacher_TeacherId(teacherId);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Optional<Student> login(String studentName) {
        return studentRepository.findByStudentName(studentName);
    }
}