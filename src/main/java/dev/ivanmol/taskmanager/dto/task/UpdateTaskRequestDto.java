package dev.ivanmol.taskmanager.dto.task;

import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import lombok.Data;

@Data
public class UpdateTaskRequestDto {
    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private Long assigneeId;
}
