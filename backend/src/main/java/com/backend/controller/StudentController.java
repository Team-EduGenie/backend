package com.backend.controller;

import com.backend.model.Student;
import com.backend.model.Teacher;
import com.backend.service.StudentService;
import com.backend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/students")
    public List<Student> getStudentAll() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/teacher/{teacherId}")
    public List<Student> getStudentsByTeacherId(@PathVariable Long teacherId) {
        return studentService.getStudentsByTeacherId(teacherId);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        Teacher teacher = teacherService.getTeacherById(student.getTeacher().getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid teacher ID"));
        student.setTeacher(teacher);
        return studentService.addStudent(student);
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/students/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String studentName = request.get("student_name");
        return studentService.login(studentName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}