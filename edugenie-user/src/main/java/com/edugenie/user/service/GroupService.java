package com.edugenie.user.service;

import com.edugenie.user.controller.dto.GroupAddRequest;
import com.edugenie.user.controller.dto.GroupJoinRequest;
import com.edugenie.user.model.Group;
import com.edugenie.user.model.User;
import com.edugenie.user.model.UserGroup;
import com.edugenie.user.repository.GroupRepository;
import com.edugenie.user.repository.UserGroupRepository;
import com.edugenie.user.repository.UserRepository;
import com.edugenie.user.service.dto.GroupInfoResult;
import com.edugenie.user.service.dto.GroupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    @Transactional(readOnly = true)
    public List<GroupResult> findGroups(String groupName, Pageable pageable) {
        List<Group> groups = groupRepository.findByGroupNameContaining(groupName, pageable).getContent();
        return groups.stream().map(GroupResult::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<GroupResult> findUserGroups(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Group> groups = groupRepository.findGroupWithUserGroupByUser(user);
        return groups.stream().map(GroupResult::fromEntity).toList();
    }

    @Transactional
    public GroupInfoResult addGroup(GroupAddRequest request) {
        User user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String inviteCode = generateInviteCode();
        Group group = Group.create(request, inviteCode, user);
        groupRepository.save(group);
        joinGroup(group.getId(), new GroupJoinRequest(user.getUserId(), inviteCode));
        return GroupInfoResult.fromEntity(group, inviteCode);
    }

    @Transactional
    public void joinGroup(Long groupId, GroupJoinRequest request) {
        User user = userRepository.findByUserId(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        if (!group.getInviteCode().equals(request.inviteCode())) {
            throw new RuntimeException("Invalid invite code");
        }
        userGroupRepository.save(UserGroup.join(user, group));
    }

    private String generateInviteCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
