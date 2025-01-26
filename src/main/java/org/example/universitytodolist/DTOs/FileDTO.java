package org.example.universitytodolist.DTOs;

import lombok.Data;

@Data
public class FileDTO {
    private Long id;

    private String fileName;

    private String filePath;

    private Long taskId;
}
