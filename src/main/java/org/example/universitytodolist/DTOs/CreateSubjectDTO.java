package org.example.universitytodolist.DTOs;

import lombok.Data;

@Data
public class CreateSubjectDTO {
    private String name;

    private String description;

    private String username;
}
