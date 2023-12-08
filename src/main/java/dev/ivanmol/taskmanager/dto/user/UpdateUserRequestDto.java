package dev.ivanmol.taskmanager.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UpdateUserRequestDto {
    @Length(min = 6, max = 254)
    @Email
    private String email;
    @Length(min = 6, max = 30)
    private String password;
}