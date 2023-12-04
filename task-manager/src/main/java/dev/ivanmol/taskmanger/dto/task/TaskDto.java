package dev.ivanmol.taskmanger.dto.task;

import dev.ivanmol.taskmanger.model.user.Priority;
import dev.ivanmol.taskmanger.model.user.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private Long authorId;
}
