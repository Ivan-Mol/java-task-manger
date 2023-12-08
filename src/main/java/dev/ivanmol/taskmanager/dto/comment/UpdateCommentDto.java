package dev.ivanmol.taskmanager.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Schema(description = "Data transfer object for comment update")
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
