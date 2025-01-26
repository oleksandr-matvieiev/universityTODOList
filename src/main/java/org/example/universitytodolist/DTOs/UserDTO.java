package org.example.universitytodolist.DTOs;

import java.util.List;

public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private List<Long> subjectIds;

    private List<Long> taskIds;
}
