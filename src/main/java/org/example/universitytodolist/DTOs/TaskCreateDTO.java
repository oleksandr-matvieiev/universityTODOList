package org.example.universitytodolist.DTOs;

import lombok.Data;
import org.example.universitytodolist.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
public class TaskCreateDTO {

    private String title;

    private String description;

    private LocalDateTime deadLine;

    private String subjectName;

}
