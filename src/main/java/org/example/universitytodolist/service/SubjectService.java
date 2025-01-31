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
    private final UserRepository userRepository;
    private final AuthService authService;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper, UserRepository userRepository, AuthService authService) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public SubjectDTO addSubject(CreateSubjectDTO createSubjectDTO) {
        Subject subject = new Subject();
        User user = userRepository.findByUsername(createSubjectDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found"));


        subject.setName(createSubjectDTO.getName());
        subject.setDescription(createSubjectDTO.getDescription());
        subject.setUser(user);

        return subjectMapper.toDTO(subjectRepository.save(subject));
    }

    public List<SubjectDTO> getAllSubjectsForCurrentUser() {
        User user = authService.getCurrentUser();
        List<Subject> subjects = user.getSubjects();
        return subjects.stream().map(subjectMapper::toDTO).collect(Collectors.toList());
    }


}
