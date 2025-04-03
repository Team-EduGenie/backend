package com.edugenie.user.model;

import com.edugenie.user.controller.dto.GroupAddRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String groupName;

    @Column(nullable = false)
    private String inviteCode;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User leader;

    @Builder.Default
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<UserGroup> userGroups = new ArrayList<>();

    public static Group create(GroupAddRequest request, String inviteCode, User user) {
        Group group = Group.builder()
                .groupName(request.groupName())
                .inviteCode(inviteCode)
                .leader(user)
                .build();
        UserGroup userGroup = UserGroup.builder()
                .user(user)
                .group(group)
                .build();
        group.userGroups.add(userGroup);
        return group;
    }
}
