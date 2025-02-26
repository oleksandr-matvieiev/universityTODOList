package org.example.universitytodolist.service;

import org.example.universitytodolist.DTOs.CreateSubjectDTO;
import org.example.universitytodolist.DTOs.SubjectDTO;
import org.example.universitytodolist.mapper.SubjectMapper;
import org.example.universitytodolist.model.Subject;
import org.example.universitytodolist.model.User;
import org.example.universitytodolist.repository.SubjectRepository;
import org.example.universitytodolist.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final AuthService authService;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper, AuthService authService) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.authService = authService;
    }

    public SubjectDTO addSubject(CreateSubjectDTO createSubjectDTO) {
        Subject subject = new Subject();
        User user = authService.getCurrentUser();


        subject.setName(createSubjectDTO.getName());
        subject.setDescription(createSubjectDTO.getDescription());
        subject.setUser(user);

        return subjectMapper.toDTO(subjectRepository.save(subject));
    }

    public List<SubjectDTO> getAllSubjectsForCurrentUser() {
        User user = authService.getCurrentUser();
        List<Subject> subjects = user.getSubjects();

        if (subjects.isEmpty()) throw new RuntimeException("Subject not found");

        return subjects.stream().map(subjectMapper::toDTO).collect(Collectors.toList());
    }


}
