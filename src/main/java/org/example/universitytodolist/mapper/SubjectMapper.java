package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.SubjectDTO;
import org.example.universitytodolist.model.Subject;
import org.example.universitytodolist.model.Task;
import org.example.universitytodolist.util.MappingUtil;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    public SubjectDTO toDTO(Subject subject) {
        SubjectDTO subjectDTO = new SubjectDTO();

        subjectDTO.setId(subject.getId());
        subjectDTO.setName(subject.getName());
        subjectDTO.setDescription(subject.getDescription());
        subjectDTO.setUserId(subject.getUser().getId());
        subjectDTO.setTaskIds(MappingUtil.mapEntitiesToIds(subject.getTasks(), Task::getId));

        return subjectDTO;
    }

}
