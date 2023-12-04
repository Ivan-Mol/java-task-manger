package dev.ivanmol.taskmanger.dto.task;

import dev.ivanmol.taskmanger.model.user.Priority;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class NewTaskRequestDto {
    @NotBlank()
    @Length(min = 2, max = 70)
    private String name;
    @NotBlank()
    @Length(min = 2, max = 3000)
    private String description;
    @NotBlank()
    private Priority priority;
}
