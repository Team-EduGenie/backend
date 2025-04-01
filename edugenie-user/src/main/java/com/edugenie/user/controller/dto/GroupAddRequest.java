package com.edugenie.user.controller.dto;

import com.edugenie.user.model.Group;

public record GroupAddRequest(
        String groupName
) {

    public Group toEntity() {
        return Group.builder()
                .groupName(groupName)
                .build();
    }
}
