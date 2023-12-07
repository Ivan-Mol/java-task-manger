package dev.ivanmol.taskmanager.dto.task;

import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateTaskRequestDto {
    @Length(min = 2, max = 70, message = "Name is shorter than 2 or longer than 70")
    private String name;
    @Length(min = 6, max = 3000, message = "Description is shorter than 2 or longer than 3000")
    private String description;
    private Priority priority;
    private Status status;
    private Long assigneeId;
}
