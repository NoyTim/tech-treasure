package de.tnoy.todo.controller;

import de.tnoy.todo.api.TodoApiDelegate;
import de.tnoy.todo.dto.ToDoDto;
import de.tnoy.todo.dto.ToDoPostDto;
import de.tnoy.todo.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ToDoController implements TodoApiDelegate {

    private final ToDoService toDoService;

    @Override
    public ResponseEntity<List<ToDoDto>> getAllToDos() {
        return ResponseEntity.ok(toDoService.getAll());
    }

    @Override
    public ResponseEntity<ToDoDto> getToDo(String id) {
        return ResponseEntity.ok(toDoService.get(id));
    }

    @Override
    public ResponseEntity<ToDoDto> postToDo(ToDoPostDto toDoPostDto) {
        return ResponseEntity.ok(toDoService.create(toDoPostDto));
    }

    @Override
    public ResponseEntity<ToDoDto> putToDo(String id, ToDoDto toDoDto) {
        toDoDto.setId(id);
        return ResponseEntity.ok(toDoService.update(toDoDto));
    }

    @Override
    public ResponseEntity<ToDoDto> deleteToDo(String id) {
        return ResponseEntity.ok(toDoService.delete(id));
    }
}
