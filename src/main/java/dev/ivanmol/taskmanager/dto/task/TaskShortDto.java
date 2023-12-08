package dev.ivanmol.taskmanager.dto.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Short Data transfer object with only Id, name and description")
@Data
public class TaskShortDto {
    private Long id;
    private String name;
    private String description;
}
