package com.edugenie.user.service.dto;

import lombok.Builder;

import java.util.Random;

@Builder
public record GroupAddResult(
        String inviteCode
) {

    public static GroupAddResult generateInviteCode() {
        String inviteCode = String.valueOf(100000 + new Random().nextInt(900000));
        return GroupAddResult.builder()
                .inviteCode(inviteCode)
                .build();
    }
}
