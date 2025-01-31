package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.TaskDTO;
import org.example.universitytodolist.model.Comment;
import org.example.universitytodolist.model.Task;
import org.example.universitytodolist.util.MappingUtil;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskDTO toDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDeadLine(task.getDeadLine());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setUploadedFile(task.getUploadedFile());
        taskDTO.setGrade(task.getGrade());
        taskDTO.setSubjectId(task.getSubject().getId());
        taskDTO.setUserId(task.getUser().getId());
        taskDTO.setGradeBookId(task.getGradeBook().getId());
        taskDTO.setCommentIds(MappingUtil.mapEntitiesToIds(task.getComments(), Comment::getId));
        if (task.getNotification() != null) {
            taskDTO.setNotificationId(task.getNotification().getId());
        }
        return taskDTO;
    }

}
