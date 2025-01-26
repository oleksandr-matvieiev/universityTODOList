package org.example.universitytodolist.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class SubjectDTO {
    private Long id;

    private String name;

    private String description;

    private Long userId;

    private List<Long> taskIds;
}
