package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.GradeBookDTO;
import org.example.universitytodolist.model.GradeBook;
import org.springframework.stereotype.Component;

@Component
public class GradeBookMapper {
    public GradeBookDTO toDTO(GradeBook gradeBook) {
        GradeBookDTO gradeBookDTO = new GradeBookDTO();

        gradeBookDTO.setId(gradeBook.getId());
        gradeBookDTO.setAverageGrade(gradeBook.getAverageGrade());
        gradeBookDTO.setUserId(gradeBook.getUser().getId());

        return gradeBookDTO;
    }

}
