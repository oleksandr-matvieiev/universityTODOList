package org.example.universitytodolist.controller;

import org.example.universitytodolist.DTOs.TaskCreateDTO;
import org.example.universitytodolist.DTOs.TaskDTO;
import org.example.universitytodolist.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/{taskId}/upload")
    public ResponseEntity<TaskDTO> uploadTask(@PathVariable("taskId") Long taskId, @RequestParam("file") MultipartFile file) throws IOException {
        TaskDTO taskDTO = taskService.uploadTask(taskId, file);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @PostMapping("/giveGrade/{taskId}")
    public ResponseEntity<TaskDTO> giveGrade(@PathVariable("taskId") Long taskId, @RequestParam("grade") Integer grade) {
        TaskDTO taskDTO = taskService.giveGradeToTask(taskId, grade);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }


    @GetMapping("/{subjectName}/get-all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@PathVariable String subjectName) {
        List<TaskDTO> taskDTOS = taskService.getAllTasksForCurrentSubject(subjectName);
        return new ResponseEntity<>(taskDTOS, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable("taskId") Long taskId) {
        TaskDTO taskDTO = taskService.getTaskById(taskId);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }


}
