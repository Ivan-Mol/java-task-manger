package dev.ivanmol.taskmanger.dto.task;

import dev.ivanmol.taskmanger.model.user.Priority;
import dev.ivanmol.taskmanger.model.user.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateTaskRequestDto {
    public class NewTaskRequestDto {
        @NotBlank
        @Length(min = 2, max = 70)
        private String name;
        @NotBlank()
        @Length(min = 2, max = 3000)
        private String description;
        @NotBlank
        private Priority priority;
        private Status status;
    }
}
