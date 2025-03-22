package com.backend.service;

import com.backend.model.Teacher;
import com.backend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    // 선생님 이름으로 조회
    public Optional<Teacher> getTeacherByName(String teacherName) {
        return teacherRepository.findByTeacherName(teacherName);
    }

    // 선생님 아이디로 조회
    public Optional<Teacher> getTeacherById(Long id) {
        return teacherRepository.findById(id);
    }

    // 🔹 3. 새로운 데이터 추가 (Create)
    public Teacher createTeacher(Teacher teacher) {
        // 이름으로 교사 찾기
        Optional<Teacher> existingTeacher = teacherRepository.findByTeacherName(teacher.getTeacherName());
        
        // 이미 존재하는 교사면 해당 교사 반환
        if (existingTeacher.isPresent()) {
            return existingTeacher.get();
        }
        
        // 존재하지 않으면 새로 생성
        return teacherRepository.save(teacher);
    }

    // 🔹 4. 데이터 수정 (Update)
    public Teacher updateTeacher(Long id, Teacher newTeacherData) {
        Teacher teacher = teacherRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("교사를 찾을 수 없습니다."));
        
        teacher.setTeacherName(newTeacherData.getTeacherName());
        return teacherRepository.save(teacher);
    }

    // 🔹 5. 데이터 삭제 (Delete)
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}
