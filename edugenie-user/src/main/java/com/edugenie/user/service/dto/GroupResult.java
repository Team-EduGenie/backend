package com.edugenie.user.service.dto;

import com.edugenie.user.model.Group;
import lombok.Builder;

@Builder
public record GroupResult(
        Long id,
        String name,
        String description
) {

    public static GroupResult fromEntity(Group group) {
        return GroupResult.builder()
                .id(group.getId())
                .name(group.getGroupName())
                .description(group.getDescription())
                .build();
    }
}
