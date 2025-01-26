package org.example.universitytodolist.mapper;

import org.example.universitytodolist.DTOs.NotificationDTO;
import org.example.universitytodolist.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDTO toDTO(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();

        notificationDTO.setId(notification.getId());
        notificationDTO.setContent(notification.getContent());
        notificationDTO.setSentAt(notification.getSentAt());
        notificationDTO.setTaskId(notification.getTask().getId());

        return notificationDTO;
    }

}
