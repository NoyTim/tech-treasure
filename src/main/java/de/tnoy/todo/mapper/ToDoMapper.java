package de.tnoy.todo.mapper;

import de.tnoy.todo.dto.ToDoDto;
import de.tnoy.todo.dto.ToDoPostDto;
import de.tnoy.todo.model.ToDoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ToDoMapper {

    ToDoDto map(ToDoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", ignore = true)
    ToDoEntity mapPostDto(ToDoPostDto entity);

    List<ToDoDto> map(List<ToDoEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget ToDoEntity entity, ToDoDto dto);

}
