package org.example.universitytodolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.universitytodolist.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime deadLine;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String uploadedFile;

    @Max(100)
    @Min(0)
    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "gradebook_id")
    private GradeBook gradeBook;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    @OneToOne(mappedBy = "task")
    private Notification notification;
}

