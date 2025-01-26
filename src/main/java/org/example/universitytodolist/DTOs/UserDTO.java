package org.example.universitytodolist.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private List<Long> subjectIds;

    private List<Long> taskIds;
}
