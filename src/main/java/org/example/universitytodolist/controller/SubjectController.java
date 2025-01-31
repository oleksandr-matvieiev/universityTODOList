package org.example.universitytodolist.controller;

import org.example.universitytodolist.DTOs.CreateSubjectDTO;
import org.example.universitytodolist.DTOs.SubjectDTO;
import org.example.universitytodolist.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody CreateSubjectDTO createSubjectDTO) {
        SubjectDTO subjectDTO = subjectService.addSubject(createSubjectDTO);
        return ResponseEntity.ok(subjectDTO);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SubjectDTO>> getAllSubjectsForCurrentUser() {
        List<SubjectDTO> subjectDTOList = subjectService.getAllSubjectsForCurrentUser();
        return ResponseEntity.ok(subjectDTOList);
    }
}
