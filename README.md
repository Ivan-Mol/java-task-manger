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

Login as default admin user
```bash
curl -X POST -H 'Content-Type: application/json' -d '{"email":"admin@taskmanager.proj","password":"_verySeqqure4dminP@$$!"}' localhost:8080/login
```

In the end shut the app down
```bash
docker compose down --volumes
```