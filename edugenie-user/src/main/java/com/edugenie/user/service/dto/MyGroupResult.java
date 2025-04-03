package com.edugenie.user.service.dto;

import com.edugenie.user.model.Group;
import com.edugenie.user.model.User;
import lombok.Builder;

@Builder
public record MyGroupResult(
        Long id,
        String name,
        String description,
        boolean isLeader
) {

    public static MyGroupResult fromEntity(Group group, User user) {
        return MyGroupResult.builder()
                .id(group.getId())
                .name(group.getGroupName())
                .description(group.getDescription())
                .isLeader(group.getLeader().getId().equals(user.getId()))
                .build();
    }
}
