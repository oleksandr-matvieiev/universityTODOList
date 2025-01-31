package org.example.universitytodolist.repository;

import org.example.universitytodolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllBySubject_IdAndUser_Id(Long subjectId, Long userId);

}
