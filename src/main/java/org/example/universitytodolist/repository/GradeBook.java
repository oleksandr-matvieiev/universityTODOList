package org.example.universitytodolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeBook extends JpaRepository<org.example.universitytodolist.model.GradeBook,Long> {
}
