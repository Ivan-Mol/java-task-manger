# Task Manager

Task manager allows users to manage tasks: create and edit, assign other users, search for tasks using filters.

## How to run

First, compile the application.

```bash
./mvnw.cmd clean package # for win
```

```bash
./mvnw clean package # for mac and *nix
```

Then run it in a Docker container

```bash
docker compose up --detach
```

Swagger is available on http://localhost:8080/swagger-ui/index.html

Use */login* endpoint to exchange credentials for JWT token
```json
{
    "email": "admin@taskmanager.proj",
    "password": "_verySeqqure4dminP@$$!"
}
```

In the end shut the app down

```bash
docker compose down --volumes
```