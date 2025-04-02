package com.edugenie.user.service;

import com.edugenie.user.controller.dto.SignupRequest;
import com.edugenie.user.model.User;
import com.edugenie.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void addUser(SignupRequest request) {
        userRepository.save(User.signup(request));
    }

    @Transactional
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

//    public Optional<User> login(String studentName) {
//        return userRepository.findByStudentName(studentName);
//    }
}
