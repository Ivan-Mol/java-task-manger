package dev.ivanmol.taskmanager.mapper;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.TaskShortDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class TasksMapper {
    public static TaskDto toTaskDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setDescription(task.getDescription());
        taskDto.setName(task.getName());
        taskDto.setStatus(task.getStatus());
        taskDto.setPriority(task.getPriority());
        taskDto.setAuthorId(task.getAuthor().getId());
        if (task.getAssignee() != null) {
            taskDto.setAssigneeId(task.getAssignee().getId());
        }
        return taskDto;
    }

    public static TaskShortDto toShortTaskDto(Task task) {
        TaskShortDto shortDto = new TaskShortDto();
        shortDto.setId(task.getId());
        shortDto.setName(task.getName());
        shortDto.setDescription(task.getDescription());
        return shortDto;
    }

    public static Task toTask(User author, NewTaskRequestDto requestDto) {
        Task newTask = new Task();
        newTask.setDescription(requestDto.getDescription());
        newTask.setName(requestDto.getName());
        newTask.setStatus(Status.NEW);
        newTask.setPriority(requestDto.getPriority());
        newTask.setAuthor(author);
        return newTask;
    }

    public static Task update(Task taskFromDb, UpdateTaskRequestDto updateDto) {
        Optional.ofNullable(updateDto.getName()).ifPresent(taskFromDb::setName);
        Optional.ofNullable(updateDto.getDescription()).ifPresent(taskFromDb::setDescription);
        Optional.ofNullable(updateDto.getStatus()).ifPresent(taskFromDb::setStatus);
        Optional.ofNullable(updateDto.getPriority()).ifPresent(taskFromDb::setPriority);
        return taskFromDb;
    }
}
