package com.edugenie.user.repository;

import com.edugenie.user.model.Group;
import com.edugenie.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findByGroupNameContaining(String groupName, Pageable pageable);

    @Query("SELECT g FROM Group g JOIN FETCH g.userGroups ug " +
            "WHERE ug.user = :user")
    List<Group> findGroupWithUserGroupByUser(User user);
}
