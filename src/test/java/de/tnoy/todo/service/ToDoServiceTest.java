package de.tnoy.todo.service;

import de.tnoy.todo.dto.ToDoDto;
import de.tnoy.todo.dto.ToDoPostDto;
import de.tnoy.todo.mapper.ToDoMapper;
import de.tnoy.todo.mapper.ToDoMapperImpl;
import de.tnoy.todo.model.ToDoEntity;
import de.tnoy.todo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { ToDoMapperImpl.class })
class ToDoServiceTest {

    private ToDoService toDoService;

    @Mock
    private ToDoRepository toDoRepository;

    @Autowired
    private ToDoMapper toDoMapper;

    @BeforeEach
    void setUp() {
        toDoService = new ToDoService(toDoRepository, toDoMapper);
    }

    @Test
    void test_getAll() {
        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setTask("Be the garbage collector in real life");

        when(toDoRepository.findAll()).thenReturn(List.of(toDoEntity));

        assertEquals(1, toDoService.getAll().size());
    }

    @Test
    void test_get_givenId_WhenToDoWithIdExists_ThenReturnToDo() {
        String toDoId = UUID.randomUUID().toString();

        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setTask("Be the garbage collector in real life");

        ToDoDto mappedFoundToDoDto = toDoMapper.map(toDoEntity);

        when(toDoRepository.findById(toDoId)).thenReturn(Optional.of(toDoEntity));

        ToDoDto expectedToDoDto = toDoService.get(toDoId);

        assertEquals(expectedToDoDto, mappedFoundToDoDto);
    }

    @Test
    void test_get_givenId_WhenToDoWithIdNotExists_ThenThrow() {
        String toDoId = UUID.randomUUID().toString();

        given(toDoRepository.findById(ArgumentMatchers.any(String.class))).willReturn(Optional.ofNullable(null));

        assertThrows(NoSuchElementException.class, () -> toDoService.get(toDoId));
    }

    @Test
    void test_create_givenToDoPostDto_whenSaved_ThenReturnSavedToDoDto() {
        ToDoPostDto toDoPostDto = new ToDoPostDto()
                .task("Just a simple task");

        ToDoEntity toDoEntity = toDoMapper.mapPostDto(toDoPostDto);

        when(toDoRepository.save(ArgumentMatchers.isA(ToDoEntity.class))).thenReturn(toDoEntity);

        ToDoDto resultToDoDto = toDoService.create(toDoPostDto);

        assertEquals(resultToDoDto.getTask(), toDoPostDto.getTask());
    }

    @Test
    void test_delete_givenId_WhenToDoWithIdExists_ThenDelete() {
        String toDoId = UUID.randomUUID().toString();
        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setId(toDoId);
        toDoEntity.setTask("Be the garbage collector in real life");
        ToDoDto deletedToDo = toDoMapper.map(toDoEntity);
        // --
        when(toDoRepository.findById(toDoId)).thenReturn(Optional.of(toDoEntity));
        toDoService.delete(toDoId);
        // --
        verify(toDoRepository,times(1)).delete(toDoEntity);
        assertEquals(deletedToDo.getId(), toDoId);
    }

    @Test
    void test_delete_givenId_WhenToDoWithIdNotExists_ThenThrow() {
        String toDoId = UUID.randomUUID().toString();

        when(toDoRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.ofNullable(null));

        assertThrows(NoSuchElementException.class, () -> toDoService.delete(toDoId));
    }

    @Test
    void test_update_givenToDoDto_whenToDoExists_ThenUpdateToDo() {
        ToDoDto toDoDto =  new ToDoDto()
                .id(UUID.randomUUID().toString())
                .task("Clean")
                .done(true);

        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setId(toDoDto.getId());
        toDoEntity.setTask("Clean");
        toDoEntity.setDone(false);

        when(toDoRepository.findById(toDoDto.getId())).thenReturn(Optional.of(toDoEntity));
        when(toDoRepository.save(toDoEntity)).thenReturn(toDoEntity);

        assertEquals(toDoDto.getDone(), toDoService.update(toDoDto).getDone());
    }


}