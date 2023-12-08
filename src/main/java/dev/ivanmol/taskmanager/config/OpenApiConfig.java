package dev.ivanmol.taskmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Task Manager",
                description = "Task management system. " +
                        "The system ensures the creation, editing, deleting and viewing tasks.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Molchanov Ivan",
                        email = "mlchnv.ivan@gmail.com",
                        url = "https://github.com/Ivan-Mol"
                )
        )
)
public class OpenApiConfig {

}