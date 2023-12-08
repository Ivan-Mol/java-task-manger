package dev.ivanmol.taskmanager.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Schema(description = "Data Transfer Object for new comment")
@Data
public class NewCommentDto {
    @NotBlank
    @Length(min = 3, max = 800)
    private String text;
}
