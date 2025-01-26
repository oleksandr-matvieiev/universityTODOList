package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.FileDTO;
import org.example.universitytodolist.model.File;
import org.springframework.stereotype.Component;

@Component
public class FileMapper {
    public FileDTO toDTO(File file) {
        FileDTO fileDTO = new FileDTO();

        fileDTO.setId(file.getId());
        fileDTO.setFileName(file.getFileName());
        fileDTO.setFileName(file.getFilePath());
        fileDTO.setTaskId(file.getTask().getId());

        return fileDTO;
    }

}
