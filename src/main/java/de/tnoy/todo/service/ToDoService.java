package de.tnoy.todo.service;

import de.tnoy.todo.dto.ToDoDto;
import de.tnoy.todo.dto.ToDoPostDto;
import de.tnoy.todo.mapper.ToDoMapper;
import de.tnoy.todo.model.ToDoEntity;
import de.tnoy.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToDoService {

   private final ToDoRepository toDoRepository;
   private final ToDoMapper toDoMapper;

   public List<ToDoDto> getAll() {
       List<ToDoEntity> allToDoEntities = toDoRepository.findAll();

       return toDoMapper.map(allToDoEntities);
   }

    public ToDoDto get(String id) {
        ToDoEntity entity = toDoRepository.findById(id).orElseThrow();

        return toDoMapper.map(entity);
    }

    public ToDoDto create(ToDoPostDto toDoPostDto) {
        ToDoEntity entity = toDoMapper.mapPostDto(toDoPostDto);
        ToDoEntity savedToDo = toDoRepository.save(entity);

        return toDoMapper.map(savedToDo);
    }

    public ToDoDto update(ToDoDto toDoDto) {
        ToDoEntity toBeUpdatedEntity = toDoRepository.findById(toDoDto.getId()).orElseThrow();
        toDoMapper.updateEntity(toBeUpdatedEntity, toDoDto);
        ToDoEntity newToDoEntity = toDoRepository.save(toBeUpdatedEntity);

        return toDoMapper.map(newToDoEntity);
    }

    public ToDoDto delete(String id) {
        ToDoEntity toBeDeletedEntity = toDoRepository.findById(id).orElseThrow();
        toDoRepository.delete(toBeDeletedEntity);

        return toDoMapper.map(toBeDeletedEntity);
    }
}
