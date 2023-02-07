package de.tnoy.todo.repository;

import de.tnoy.todo.model.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, String> {

    Optional<ToDoEntity> findById(String id);
}
