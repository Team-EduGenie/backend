package com.edugenie.user.controller.dto;

public record LoginRequest(
        String userId,
        String password
) {
}
