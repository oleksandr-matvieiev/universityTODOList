package org.example.universitytodolist.DTOs;

import lombok.Data;

@Data
public class GradeBookDTO {
    private Long id;

    private Long userId;

    private Double averageGrade;
}
