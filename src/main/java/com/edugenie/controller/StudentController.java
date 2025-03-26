package com.edugenie.controller;

import com.edugenie.model.Student;
import com.edugenie.model.Teacher;
import com.edugenie.service.StudentService;
import com.edugenie.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping
    public List<Student> getStudentAll() {
        return studentService.getAllStudents();
    }

    @GetMapping("/teacher/{teacherId}")
    public List<Student> getStudentsByTeacherId(@PathVariable Long teacherId) {
        return studentService.getStudentsByTeacherId(teacherId);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        Teacher teacher = teacherService.getTeacherById(student.getTeacher().getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID"));
        student.setTeacher(teacher);
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String studentName = request.get("student_name");
        return studentService.login(studentName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}