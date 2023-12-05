package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "users/{authorId}/tasks")
public class PrivateTaskController {
    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid NewTaskRequestDto requestDto,
                              @PathVariable Long authorId) {
        log.info("POST/createTask with dto " + requestDto + " by user with id:" + authorId);
        return taskService.createTask(authorId, requestDto);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long authorId,
                           @PathVariable Long taskId) {
        log.info("DELETE/deleteTaskByAuthor with taskId " + taskId);
        taskService.deleteTaskByAuthor(authorId, taskId);
    }

    @PatchMapping("/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateTaskByAuthor(@PathVariable Long authorId,
                                      @PathVariable Long taskId,
                                      @RequestBody UpdateTaskRequestDto updateDto) {
        log.info("PATCH/updateTaskByAuthor with taskId " + authorId + "task: " + updateDto);
        return taskService.updateTaskByAuthor(authorId, taskId, updateDto);
    }
}
