package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.UserDTO;
import org.example.universitytodolist.model.Subject;
import org.example.universitytodolist.model.Task;
import org.example.universitytodolist.model.User;
import org.example.universitytodolist.util.MappingUtil;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(userDTO.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setSubjectIds(MappingUtil.mapEntitiesToIds(user.getSubjects(), Subject::getId));
        userDTO.setTaskIds(MappingUtil.mapEntitiesToIds(user.getTasks(), Task::getId));

        return userDTO;
    }

}
