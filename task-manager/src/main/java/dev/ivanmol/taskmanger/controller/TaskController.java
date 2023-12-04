package dev.ivanmol.taskmanger.controller;

import dev.ivanmol.taskmanger.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanger.dto.task.TaskDto;
import dev.ivanmol.taskmanger.model.user.Priority;
import dev.ivanmol.taskmanger.model.user.Status;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/tasks")
public class TaskController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(@RequestBody @Valid NewTaskRequestDto taskRequestDto, @PathVariable Long authorId) {
        log.info("POST/createTask with dto " + taskRequestDto+" by user with id:"+authorId);
        return new TaskDto(1L,"Create API","About task", Status.NEW, Priority.HIGH,1L);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@PathVariable Long id){
        return new TaskDto(1L,"Create API","About task", Status.NEW, Priority.HIGH,1L);
    }
}
