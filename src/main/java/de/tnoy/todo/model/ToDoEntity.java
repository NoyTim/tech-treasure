package de.tnoy.todo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "todo")
public class ToDoEntity {

    @Id
    @Size(min = 36, max = 36, message = "ID invalid.")
    String id = UUID.randomUUID().toString();

    @Column(name = "task", nullable = false)
    @NotEmpty
    String task;

    @Column(name = "done", nullable = false)
    @NotNull
    boolean done = false;
}
