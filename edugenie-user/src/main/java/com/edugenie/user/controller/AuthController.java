package com.edugenie.user.controller;

import com.edugenie.user.controller.dto.LoginRequest;
import com.edugenie.user.service.UserService;
import com.edugenie.user.service.dto.UserInfoResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserInfoResult> login(@RequestBody LoginRequest request) {
        UserInfoResult result = userService.login(request);
        return ResponseEntity.ok(result);
    }
}
