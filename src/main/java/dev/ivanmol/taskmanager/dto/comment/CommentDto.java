package dev.ivanmol.taskmanager.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Data Transfer Object with date of creation, author id and task id")
@Data
public class CommentDto {
    private Long id;
    private String text;
    private Long authorId;
    private Long taskId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
}
