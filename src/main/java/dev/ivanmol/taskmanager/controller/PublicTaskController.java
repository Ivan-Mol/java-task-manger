package dev.ivanmol.taskmanager.controller;

import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.TaskShortDto;
import dev.ivanmol.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "tasks")
public class PublicTaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDto getTaskById(@PathVariable Long id){
        log.info("GET/getTaskById with id " + id);
        return taskService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<TaskShortDto> getAllTasks(@RequestParam(required = false) List<Long> ids,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET/getAllTasks with ids " + ids + " from: " + from + " size: " + size);
        return taskService.getAllTasks(ids, from, size);
    }
}
