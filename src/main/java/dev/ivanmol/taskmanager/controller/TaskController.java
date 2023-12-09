package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task controller", description = "Controller for managing tasks")
@RequestMapping(path = "/tasks")
@Validated
public class TaskController {
    private final TaskService taskService;

    @Operation(
            summary = "Create task",
            description = "Allows you to create a task"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/my")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Parameter(description = "Request DTO for Task")
                              @RequestBody @Valid NewTaskRequestDto requestDto,
                              @AuthenticationPrincipal User user) {
        log.info("POST/createTask with dto " + requestDto + " by user with id:" + user.getId());
        return taskService.createTask(user.getId(), requestDto);
    }

    @Operation(
            summary = "Delete task",
            description = "Allows you to delete a task"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/my/{taskId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@Parameter(description = "Task Id")
                           @PathVariable Long taskId,
                           @AuthenticationPrincipal User user) {
        log.info("DELETE/deleteTaskByAuthor with taskId " + taskId);
        taskService.deleteTaskByAuthor(user.getId(), taskId);
    }

    @Operation(
            summary = "Update task by Author",
            description = "Allows an author to update a task"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/my/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateTaskByAuthor(@Parameter(description = "Task Id")
                                      @PathVariable Long taskId,
                                      @Parameter(description = "Update DTO for Task")
                                      @Valid @RequestBody UpdateTaskRequestDto updateDto,
                                      @AuthenticationPrincipal User user) {
        log.info("PATCH/updateTaskByAuthor with taskId " + user.getId() + " " + updateDto);
        return taskService.updateTaskByAuthor(user.getId(), taskId, updateDto);
    }

    @Operation(
            summary = "Update status of task by an assignee",
            description = "Update status of task by an assignee"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PatchMapping("/my/assigned/{taskId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateStatusByAssignee(@Parameter(description = "Task id")
                                          @PathVariable Long taskId,
                                          @Parameter(description = "Status(NEW, IN_PROGRESS,DONE)")
                                          @RequestParam(name = "status") Status status,
                                          @AuthenticationPrincipal User user) {
        log.info("PATCH/updateStatusByAssignee with id " + user.getId() + " status request: " + status);
        return taskService.updateStatusByAssignee(user.getId(), taskId, status);
    }

    @Operation(
            summary = "Get all tasks",
            description = "Get all tasks with pagination"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<TaskDto> getAllTasks(@Parameter(description = "Author", required = false)
                                           @RequestParam(required = false) Long authorId,
                                           @Parameter(description = "Assignee", required = false)
                                           @RequestParam(required = false) Long assigneeId,
                                           @Parameter(description = "From", required = false)
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @Parameter(description = "Size", required = false)
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/getAllTasks with author {} assignee {} from {} size {}", authorId, assigneeId, from, size);
        return taskService.getAllTasks(authorId, assigneeId, from, size);
    }

    @Operation(
            summary = "Get task by taskId",
            description = "Allows you to get a task by Id"
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@Parameter(description = "Task Id")
                               @PathVariable Long taskId) {
        log.info("GET/getTaskById with taskId " + taskId);
        return taskService.getById(taskId);
    }
}
