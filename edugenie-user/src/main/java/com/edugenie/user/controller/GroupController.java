package com.edugenie.user.controller;

import com.edugenie.user.controller.dto.GroupAddRequest;
import com.edugenie.user.controller.dto.GroupJoinRequest;
import com.edugenie.user.service.GroupService;
import com.edugenie.user.service.dto.GroupAddResult;
import com.edugenie.user.service.dto.GroupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResult>> groupList(@PageableDefault(sort = "id") Pageable pageable, @RequestParam String groupName) {
        List<GroupResult> results = groupService.findGroups(groupName, pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<GroupResult>> groupDetails(@PathVariable Long userId) {
        List<GroupResult> results = groupService.findUserGroups(userId);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<GroupAddResult> groupAdd(@RequestBody GroupAddRequest request) {
        GroupAddResult result = groupService.addGroup(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<Void> groupJoin(@PathVariable Long groupId, @RequestBody GroupJoinRequest request) {
        groupService.joinGroup(groupId, request);
        return ResponseEntity.ok().build();
    }
}
