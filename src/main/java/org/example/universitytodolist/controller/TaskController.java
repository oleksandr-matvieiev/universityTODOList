package org.example.universitytodolist.controller;

import org.example.universitytodolist.DTOs.TaskCreateDTO;
import org.example.universitytodolist.DTOs.TaskDTO;
import org.example.universitytodolist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskCreateDTO taskCreateDTO) {
        TaskDTO taskDTO = taskService.createTask(taskCreateDTO);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{subjectName}/get-all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable String subjectName) {
        List<TaskDTO> taskDTOS = taskService.getAllTasksForCurrentSubject(subjectName);
        return new ResponseEntity<>(taskDTOS, HttpStatus.OK);
    }
}
