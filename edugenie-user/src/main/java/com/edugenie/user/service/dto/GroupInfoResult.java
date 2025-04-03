package com.edugenie.user.service.dto;

import com.edugenie.user.model.Group;
import lombok.Builder;

@Builder
public record GroupInfoResult(
        Long groupId,
        String groupName,
        String inviteCode
) {

    public static GroupInfoResult fromEntity(Group group, String inviteCode) {
        return GroupInfoResult.builder()
                .groupId(group.getId())
                .groupName(group.getGroupName())
                .inviteCode(inviteCode)
                .build();
    }
}
