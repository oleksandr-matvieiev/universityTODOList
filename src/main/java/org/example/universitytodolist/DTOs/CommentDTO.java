package org.example.universitytodolist.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    private Long taskId;

    private Long userId;
}
