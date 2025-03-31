package com.edugenie.repository;

import com.edugenie.model.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findByStudentIdOrderByTimestampDesc(Long studentId);
}
