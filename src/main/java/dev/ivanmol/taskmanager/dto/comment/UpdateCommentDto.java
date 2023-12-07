package dev.ivanmol.taskmanager.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class UpdateCommentDto {
    @NotBlank
    @Length(min = 3, max = 800)
    private String text;
    private Long authorId;
    private Long taskId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
}
