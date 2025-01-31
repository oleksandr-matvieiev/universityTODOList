package org.example.universitytodolist.repository;

import org.example.universitytodolist.model.GradeBook;
import org.example.universitytodolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeBookRepository extends JpaRepository<org.example.universitytodolist.model.GradeBook, Long> {
    Optional<GradeBook> findByUser(User user);

}
