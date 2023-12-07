package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.service.TaskService;
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
@RequestMapping(path = "/tasks")
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/users/{authorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid NewTaskRequestDto requestDto,
                              @PathVariable Long authorId) {
        log.info("POST/createTask with dto " + requestDto + " by user with id:" + authorId);
        return taskService.createTask(authorId, requestDto);
    }

    @DeleteMapping("/{taskId}/users/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long authorId,
                           @PathVariable Long taskId) {
        log.info("DELETE/deleteTaskByAuthor with taskId " + taskId);
        taskService.deleteTaskByAuthor(authorId, taskId);
    }

    @PatchMapping("/{taskId}/users/{authorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateTaskByAuthor(@PathVariable Long authorId,
                                      @PathVariable Long taskId,
                                      @Validated @RequestBody UpdateTaskRequestDto updateDto) {
        log.info("PATCH/updateTaskByAuthor with taskId " + authorId + " " + updateDto);
        return taskService.updateTaskByAuthor(authorId, taskId, updateDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@PathVariable Long id) {
        log.info("GET/getTaskById with id " + id);
        return taskService.getById(id);
    }

    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getTaskByAuthor(@PathVariable Long id) {
        log.info("GET/getTaskByAuthor with id " + id);
        return taskService.getByAuthor(id);
    }

    @GetMapping("/assignee/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDto> getTasksByAssignee(@PathVariable Long id) {
        log.info("GET/getTaskByAssignee with id " + id);
        return taskService.getTasksByAssignee(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<TaskDto> getAllTasks(@RequestParam(required = false) List<Long> ids,
                                           @RequestParam(defaultValue = "0") Integer from,
                                           @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/getAllTasks with ids " + ids + " from: " + from + " size: " + size);
        return taskService.getAllTasks(ids, from, size);
    }

    @PatchMapping("/{taskId}/assignee/{assigneeId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto updateStatusByAssignee(@PathVariable Long assigneeId,
                                          @PathVariable Long taskId,
                                          @RequestParam(name = "status") Status status) {
        log.info("PATCH/updateStatusByAssignee with id " + assigneeId + " status request: " + status);
        return taskService.updateStatusByAssignee(assigneeId, taskId, status);
    }
}
