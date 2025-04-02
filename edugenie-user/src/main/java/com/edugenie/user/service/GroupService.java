package com.edugenie.user.service;

import com.edugenie.user.controller.dto.GroupAddRequest;
import com.edugenie.user.controller.dto.GroupJoinRequest;
import com.edugenie.user.model.Group;
import com.edugenie.user.model.User;
import com.edugenie.user.repository.GroupRepository;
import com.edugenie.user.repository.UserRepository;
import com.edugenie.user.service.dto.GroupAddResult;
import com.edugenie.user.service.dto.GroupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<GroupResult> findGroups(String groupName, Pageable pageable) {
        List<Group> groups = groupRepository.findByGroupNameContaining(groupName, pageable).getContent();
        return groups.stream().map(GroupResult::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<GroupResult> findUserGroups(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        List<Group> groups = groupRepository.findGroupWithUserGroupByUser(user);
        return groups.stream().map(GroupResult::fromEntity).toList();
    }

    @Transactional
    public GroupAddResult addGroup(GroupAddRequest request) {
        groupRepository.save(request.toEntity());
        return GroupAddResult.generateInviteCode();
    }

    @Transactional
    public void joinGroup(Long groupId, GroupJoinRequest request) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        if (!group.getInviteCode().equals(request.inviteCode())) {
            throw new RuntimeException("Invalid invite code");
        }
    }
}
