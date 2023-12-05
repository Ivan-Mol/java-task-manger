package dev.ivanmol.taskmanager.dto.task;

import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private Long authorId;
    private Long performerId;
}
