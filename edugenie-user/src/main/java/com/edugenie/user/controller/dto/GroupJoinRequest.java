package com.edugenie.user.controller.dto;

public record GroupJoinRequest(
        String userId,
        String inviteCode
) {
}
