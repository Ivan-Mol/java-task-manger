package dev.ivanmol.taskmanager.dto.task;

import dev.ivanmol.taskmanager.model.task.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewTaskRequestDto {
    @NotBlank
    @Length(min = 2, max = 70)
    private String name;
    @NotBlank
    @Length(min = 2, max = 3000)
    private String description;
    private Priority priority;
}
