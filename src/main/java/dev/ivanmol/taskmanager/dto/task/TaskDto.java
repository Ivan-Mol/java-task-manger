package dev.ivanmol.taskmanager.dto.task;

import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Data Transfer Object with Id and a status, an author and an assignee")
@Data
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private Status status;
    private Priority priority;
    private Long authorId;
    private Long assigneeId;
}
