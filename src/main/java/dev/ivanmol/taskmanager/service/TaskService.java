package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.model.task.Status;

import java.util.Collection;
import java.util.List;

//Пользователи могут управлять своими задачами: создавать новые,
//редактировать существующие, просматривать и удалять, менять статус и
//назначать исполнителей задачи.
//4
//Пользователи могут просматривать задачи других пользователей, а
//исполнители задачи могут менять статус своих задач.
//5
//К задачам можно оставлять комментарии.
//6
//API должно позволять получать задачи конкретного автора или исполнителя, а
//также все комментарии к ним. Необходимо обеспечить фильтрацию и
//пагинацию вывода.
//7
public interface TaskService {

    TaskDto getById(Long id);

    TaskDto createTask(Long authorId, NewTaskRequestDto requestDto);

    TaskDto updateTaskByAuthor(Long authorId, Long taskId, UpdateTaskRequestDto requestDto);

    void deleteTaskByAuthor(Long authorId, Long taskId);

    Collection<TaskDto> getAllTasks(List<Long> ids, Integer from, Integer size);

    List<TaskDto> getByAuthor(Long id);

    List<TaskDto> getTasksByAssignee(Long id);

    TaskDto updateStatusByAssignee(Long assigneeId, Long taskId, Status updateDto);
}
