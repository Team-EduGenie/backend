package com.edugenie.user.controller.dto;

import com.edugenie.user.model.Group;

public record GroupAddRequest(
        String userId,
        String groupName
) {

    public Group toEntity(String inviteCode) {
        return Group.builder()
                .groupName(groupName)
                .inviteCode(inviteCode)
                .build();
    }
}
