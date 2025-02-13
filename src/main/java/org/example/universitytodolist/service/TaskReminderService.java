package org.example.universitytodolist.service;

import org.example.universitytodolist.enums.TaskStatus;
import org.example.universitytodolist.model.Task;
import org.example.universitytodolist.model.User;
import org.example.universitytodolist.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskReminderService {
    private final TaskRepository taskRepository;
    private final EmailService emailService;


    public TaskReminderService(TaskRepository taskRepository, EmailService emailService) {
        this.taskRepository = taskRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 10 * * ?")
//    @Scheduled(fixedRate = 5000)
    public void reminder() {
        LocalDateTime treeDayLater = LocalDateTime.now().plusDays(3);
        List<Task> tasks = taskRepository.findAllByDeadLineBetween(LocalDateTime.now(), treeDayLater);

        for (Task task : tasks) {
            if (!task.isReminderSent() && task.getStatus() != TaskStatus.COMPLETED) {
                User user = task.getUser();
                String subject = "\uD83D\uDD14 Reminder about deadline:" + task.getTitle();
                String message = "Hi, " + user.getUsername() + "!<br><br>" +
                        "We would like to inform you that the deadline for the task<b>" + task.getTitle() + "</b>" +
                        " is approaching. You have <b>3 more days</b> to complete it.<br><br>" +
                        "<i>Sincerely, UniversityToDoList</i>";
                emailService.sendEmail(user.getEmail(), subject, message);
                task.setReminderSent(true);
                taskRepository.save(task);
            }
        }
        System.out.println("Reminder completed");
    }
}
