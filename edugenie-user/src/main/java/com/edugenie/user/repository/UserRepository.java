package com.edugenie.user.repository;

import com.edugenie.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    Optional<User> findByStudentName(String studentName);
}
