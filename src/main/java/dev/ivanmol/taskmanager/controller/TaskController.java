package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task controller", description = "Controller for managing tasks")
@RequestMapping(path = "/tasks")
public class TaskController {
    private final TaskService taskService;

    @Operation(
            summary = "Create task",
            description = "Allows you to create a task"
    )
    @PostMapping("/users/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@Parameter(description = "Request DTO for Task")
                              @RequestBody @Valid NewTaskRequestDto requestDto,
                              @Parameter(description = "Author ID")
                              @PathVariable Long authorId) {
        log.info("POST/createTask with dto " + requestDto + " by user with id:" + authorId);
        return taskService.createTask(authorId, requestDto);
    }

    @Operation(
            summary = "Delete task",
            description = "Allows you to delete a task"
    )
    @DeleteMapping("/{taskId}/users/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@Parameter(description = "Author Id")
                           @PathVariable Long authorId,
                           @Parameter(description = "Task Id")
                           @PathVariable Long taskId) {
        log.info("DELETE/deleteTaskByAuthor with taskId " + taskId);
        taskService.deleteTaskByAuthor(authorId, taskId);
    }

    @Operation(
            summary = "Update task by Author",
            description = "Allows an author to update a task"
    )
    @PatchMapping("/{taskId}/users/{authorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateTaskByAuthor(@Parameter(description = "Author Id")
                                      @PathVariable Long authorId,
                                      @Parameter(description = "Task Id")
                                      @PathVariable Long taskId,
                                      @Parameter(description = "Update DTO for Task")
                                      @Validated @RequestBody UpdateTaskRequestDto updateDto) {
        log.info("PATCH/updateTaskByAuthor with taskId " + authorId + " " + updateDto);
        return taskService.updateTaskByAuthor(authorId, taskId, updateDto);
    }

    @Operation(
            summary = "Get task by taskId",
            description = "Allows you to get a task by Id"
    )
    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@Parameter(description = "Task Id")
                               @PathVariable Long taskId) {
        log.info("GET/getTaskById with taskId " + taskId);
        return taskService.getById(taskId);
    }

    @Operation(
            summary = "Get task by author",
            description = "Allows an author to get a task"
    )
    @GetMapping("/author/{authorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getTaskByAuthor(@Parameter(description = "Author Id")
                                         @PathVariable Long authorId) {
        log.info("GET/getTaskByAuthor with authorId " + authorId);
        return taskService.getByAuthor(authorId);
    }

    @Operation(
            summary = "Get tasks by assignee",
            description = "Allows an assignee to get tasks with pagination"
    )
    @GetMapping("/assignee/{assigneeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getTasksByAssignee(@Parameter(description = "Assignee Id")
                                            @PathVariable Long assigneeId) {
        log.info("GET/getTaskByAssignee with assigneeId " + assigneeId);
        return taskService.getTasksByAssignee(assigneeId);
    }

    @Operation(
            summary = "Get all tasks",
            description = "Get all tasks with pagination"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<TaskDto> getAllTasks(@Parameter(description = "Tasks id", required = false)
                                           @RequestParam(required = false) List<Long> ids,
                                           @Parameter(description = "From", required = false)
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @Parameter(description = "Size", required = false)
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/getAllTasks with ids " + ids + " from: " + from + " size: " + size);
        return taskService.getAllTasks(ids, from, size);
    }

    @Operation(
            summary = "Update status of task by an assignee",
            description = "Update status of task by an assignee"
    )
    @PatchMapping("/{taskId}/assignee/{assigneeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateStatusByAssignee(@Parameter(description = "Assignee id")
                                          @PathVariable Long assigneeId,
                                          @Parameter(description = "Task id")
                                          @PathVariable Long taskId,
                                          @Parameter(description = "Status(NEW, IN_PROGRESS,DONE)")
                                          @RequestParam(name = "status") Status status) {
        log.info("PATCH/updateStatusByAssignee with id " + assigneeId + " status request: " + status);
        return taskService.updateStatusByAssignee(assigneeId, taskId, status);
    }
}
