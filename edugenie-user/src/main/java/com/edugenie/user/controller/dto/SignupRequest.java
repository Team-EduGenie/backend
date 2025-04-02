package com.edugenie.user.controller.dto;

public record SignupRequest(
        String username,
        String userId,
        String password
) {
}
