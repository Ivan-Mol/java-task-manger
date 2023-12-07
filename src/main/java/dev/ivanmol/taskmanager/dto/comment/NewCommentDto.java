package dev.ivanmol.taskmanager.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class NewCommentDto {
    @NotBlank
    @Length(min = 3, max = 800)
    private String text;
}
