package dev.ivanmol.taskmanager.dto.task;

import lombok.Data;

@Data
public class TaskShortDto {
    private Long id;
    private String name;
    private String description;
}
