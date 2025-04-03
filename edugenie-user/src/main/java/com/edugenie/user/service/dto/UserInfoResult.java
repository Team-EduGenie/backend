package com.edugenie.user.service.dto;

import com.edugenie.user.model.User;
import lombok.Builder;

@Builder
public record UserInfoResult(
        String userId,
        String accessToken
) {

    public static UserInfoResult fromEntity(User user, String accessToken) {
        return UserInfoResult.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .build();
    }
}
