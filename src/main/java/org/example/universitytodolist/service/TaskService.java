package org.example.universitytodolist.service;

import org.example.universitytodolist.DTOs.TaskCreateDTO;
import org.example.universitytodolist.DTOs.TaskDTO;
import org.example.universitytodolist.enums.TaskStatus;
import org.example.universitytodolist.mapper.TaskMapper;
import org.example.universitytodolist.model.GradeBook;
import org.example.universitytodolist.model.Subject;
import org.example.universitytodolist.model.Task;
import org.example.universitytodolist.model.User;
import org.example.universitytodolist.repository.GradeBookRepository;
import org.example.universitytodolist.repository.SubjectRepository;
import org.example.universitytodolist.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final AuthService authService;
    private final SubjectRepository subjectRepository;
    private final GradeBookRepository gradeBookRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, AuthService authService, SubjectRepository subjectRepository, GradeBookRepository gradeBookRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.authService = authService;
        this.subjectRepository = subjectRepository;
        this.gradeBookRepository = gradeBookRepository;
    }

    public TaskDTO createTask(TaskCreateDTO taskCreateDTO) {
        Task task = new Task();
        User user = authService.getCurrentUser();
        Subject subject = subjectRepository.findByName(taskCreateDTO.getSubjectName())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        GradeBook gradeBook = gradeBookRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("GradeBook not found"));


        task.setTitle(taskCreateDTO.getTitle());
        task.setDescription(taskCreateDTO.getDescription());
        task.setDeadLine(taskCreateDTO.getDeadLine());
        task.setStatus(TaskStatus.PENDING);
        task.setGradeBook(gradeBook);
        task.setSubject(subject);
        task.setUser(user);

        return taskMapper.toDTO(taskRepository.save(task));
    }

    public List<TaskDTO> getAllTasksForCurrentSubject(String subjectName) {
        User user = authService.getCurrentUser();
        Subject subject = subjectRepository.findByName(subjectName)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (subject == null || !user.getSubjects().contains(subject)) {
            throw new RuntimeException("Wrong subject");
        }

        List<Task> tasks = taskRepository.findAllBySubject_IdAndUser_Id(subject.getId(), user.getId());

        return tasks.stream().map(taskMapper::toDTO).collect(Collectors.toList());
    }


}
