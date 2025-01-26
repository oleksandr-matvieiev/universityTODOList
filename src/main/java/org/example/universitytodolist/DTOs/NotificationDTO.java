package org.example.universitytodolist.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;

    private String content;

    private LocalDateTime sentAt;

    private Long taskId;
}
