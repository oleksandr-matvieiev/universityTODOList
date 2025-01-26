package org.example.universitytodolist.DTOs;

import lombok.Data;
import org.example.universitytodolist.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDTO {
    private Long id;

    private String title;

    private String description;

    private LocalDateTime deadLine;

    private TaskStatus status;

    private String uploadedFile;

    private Integer grade;

    private Long subjectId;

    private Long userId;

    private Long gradeBookId;

    private List<Long> commentIds;

    private Long notificationId;
}
