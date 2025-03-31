package com.edugenie.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private String role; // "USER" or "AI"

    @Column(columnDefinition = "TEXT")
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ChatLog(Long studentId, String role, String message) {
        this.studentId = studentId;
        this.role = role;
        this.message = message;
    }

}
