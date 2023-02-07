package de.tnoy.todo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

import java.util.Arrays;
import java.util.List;

import de.tnoy.todo.dto.ToDoDto;
import de.tnoy.todo.dto.ToDoPostDto;
import de.tnoy.todo.service.ToDoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {

    @Mock
    private ToDoService toDoService;

    @InjectMocks
    private ToDoController toDoController;

    private ToDoDto toDoDto;
    private ToDoPostDto toDoPostDto;

    @BeforeEach
    public void setUp() {
        toDoDto = new ToDoDto();
        toDoPostDto = new ToDoPostDto();
    }

    @Test
    public void getAllToDosTest() {
        List<ToDoDto> toDoDtoList = Arrays.asList(toDoDto);
        when(toDoService.getAll()).thenReturn(toDoDtoList);

        ResponseEntity<List<ToDoDto>> response = toDoController.getAllToDos();

        assertEquals(OK, response.getStatusCode());
        assertEquals(toDoDtoList, response.getBody());
    }

    @Test
    public void getToDoTest() {
        String id = "1";
        when(toDoService.get(eq(id))).thenReturn(toDoDto);

        ResponseEntity<ToDoDto> response = toDoController.getToDo(id);

        assertEquals(OK, response.getStatusCode());
        assertEquals(toDoDto, response.getBody());
    }

    @Test
    public void postToDoTest() {
        when(toDoService.create(any(ToDoPostDto.class))).thenReturn(toDoDto);

        ResponseEntity<ToDoDto> response = toDoController.postToDo(toDoPostDto);

        assertEquals(OK, response.getStatusCode());
        assertEquals(toDoDto, response.getBody());
    }

    public void putToDoTest() {
        when(toDoService.update(toDoDto)).thenReturn(toDoDto);

        ResponseEntity<ToDoDto> response = toDoController.putToDo("id", toDoDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDoDto, response.getBody());

        verify(toDoService).update(toDoDto);
    }

    @Test
    public void deleteToDoTest() {
        when(toDoService.delete("id")).thenReturn(toDoDto);

        ResponseEntity<ToDoDto> response = toDoController.deleteToDo("id");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDoDto, response.getBody());

        verify(toDoService).delete("id");
    }
}