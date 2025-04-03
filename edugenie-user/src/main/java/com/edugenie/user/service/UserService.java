package com.edugenie.user.service;

import com.edugenie.user.controller.dto.LoginRequest;
import com.edugenie.user.controller.dto.SignupRequest;
import com.edugenie.user.model.User;
import com.edugenie.user.repository.UserRepository;
import com.edugenie.user.service.dto.UserInfoResult;
import com.edugenie.user.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Transactional
    public void addUser(SignupRequest request) {
        userRepository.save(User.signup(request));
    }

    @Transactional
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserInfoResult login(LoginRequest request) {
        User user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(request.password())) {
            throw new RuntimeException("Invalid password");
        }
        String accessToken = jwtUtil.generateToken(String.valueOf(user.getUserId()));
        return UserInfoResult.fromEntity(user, accessToken);
    }
}
