package com.edugenie.user.controller;

import com.edugenie.user.controller.dto.SignupRequest;
import com.edugenie.user.service.GroupService;
import com.edugenie.user.service.UserService;
import com.edugenie.user.service.dto.GroupResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final GroupService groupService;

    @GetMapping("/{userId}/groups")
    public ResponseEntity<List<GroupResult>> userGroupList(@PathVariable String userId) {
        List<GroupResult> results = groupService.findUserGroups(userId);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    public ResponseEntity<Void> signup(@RequestBody SignupRequest request) {
        userService.addUser(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        userService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}
