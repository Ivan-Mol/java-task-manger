package dev.ivanmol.taskmanager.dto.task;

import dev.ivanmol.taskmanager.model.task.Priority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Data Transfer Object for new task")
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
