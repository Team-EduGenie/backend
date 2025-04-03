package com.edugenie.user.service.dto;

import com.edugenie.user.model.Group;
import lombok.Builder;

@Builder
public record GroupResult(
        Long groupId,
        String groupName,
        String description
) {

    public static GroupResult fromEntity(Group group) {
        return GroupResult.builder()
                .groupId(group.getId())
                .groupName(group.getGroupName())
                .description(group.getDescription())
                .build();
    }
}
