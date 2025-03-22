package com.backend.service;

import com.backend.model.Student;
import com.backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    

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