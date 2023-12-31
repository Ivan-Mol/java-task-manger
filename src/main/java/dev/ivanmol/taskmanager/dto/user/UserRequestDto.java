package dev.ivanmol.taskmanager.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserRequestDto {
    @NotBlank
    @Email
    @Length(min = 6, max = 254)
    private String email;
    @NotBlank
    @Length(min = 6, max = 30)
    private String password;
}