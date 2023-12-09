package dev.ivanmol.taskmanager.service.impl;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.exception.ValidationException;
import dev.ivanmol.taskmanager.mapper.TasksMapper;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.repository.TaskRepository;
import dev.ivanmol.taskmanager.repository.UserRepository;
import dev.ivanmol.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public TaskDto getById(Long id) {
        return TasksMapper.toTaskDto(taskRepository.getByIdAndCheck(id));
    }

    @Override
    public TaskDto createTask(Long authorId, NewTaskRequestDto requestDto) {
        User taskAuthor = userRepository.getByIdAndCheck(authorId);
        Task newTask = TasksMapper.toTask(taskAuthor, requestDto);
        log.debug("Task with id = {}, was created", newTask);
        return TasksMapper.toTaskDto(taskRepository.save(newTask));
    }

    @Override
    public TaskDto updateTaskByAuthor(Long authorId, Long taskId, UpdateTaskRequestDto requestDto) {
        User author = userRepository.getByIdAndCheck(authorId);
        Task taskFromDb = taskRepository.getByIdAndCheck(taskId);
        isUserTaskAuthor(author, taskFromDb);
        checkAndSetAssigneeToTask(requestDto, taskFromDb);
        Task updatedTask = TasksMapper.update(taskFromDb, requestDto);
        return TasksMapper.toTaskDto(taskRepository.save(updatedTask));
    }

    @Override
    public void deleteTaskByAuthor(Long authorId, Long taskId) {
        User taskAuthor = userRepository.getByIdAndCheck(authorId);
        Task task = taskRepository.getByIdAndCheck(taskId);
        isUserTaskAuthor(taskAuthor, task);
        taskRepository.deleteById(taskId);
    }

    @Override
    public Collection<TaskDto> getAllTasks(Long authorId, Long assigneeId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return getAllTasks(authorId, assigneeId, pageable)
                .stream()
                .map(TasksMapper::toTaskDto)
                .toList();
    }

    private List<Task> getAllTasks(Long authorId, Long assigneeId, Pageable pageable) {
        if (authorId != null) {
            User author = userRepository.getByIdAndCheck(authorId);
            if (assigneeId != null) {
                User assignee = userRepository.getByIdAndCheck(assigneeId);
                return taskRepository.getAllByAuthorAndAssigneeOrderByIdDesc(author, assignee, pageable);
            } else {
                return taskRepository.getAllByAuthorOrderByIdDesc(author, pageable);
            }
        } else if (assigneeId != null) {
            User assignee = userRepository.getByIdAndCheck(assigneeId);
            return taskRepository.getAllByAssigneeOrderByIdDesc(assignee, pageable);
        } else {
            return taskRepository.getAllByOrderByIdDesc(pageable);
        }
    }

    @Override
    public TaskDto updateStatusByAssignee(Long assigneeId, Long taskId, Status status) {
        User assignee = userRepository.getByIdAndCheck(assigneeId);
        Task task = taskRepository.getByIdAndCheck(taskId);
        isUserTaskAssignee(assignee, task);
        task.setStatus(status);
        return TasksMapper.toTaskDto(taskRepository.save(task));
    }

    private void isUserTaskAuthor(User user, Task task) {
        if (!user.getId().equals(task.getAuthor().getId())) {
            throw new ValidationException("User with id " + user.getId() + " is not task owner: " + task.getId());
        }
    }

    private void isUserTaskAssignee(User user, Task task) {
        if (!user.getId().equals(task.getAssignee().getId())) {
            throw new ValidationException("User with id " + user.getId() + " is not task assignee: " + task.getId());
        }
    }

    private void checkAndSetAssigneeToTask(UpdateTaskRequestDto requestDto, Task taskFromDb) {
        if (requestDto.getAssigneeId() != null) {
            taskFromDb.setAssignee(userRepository.getByIdAndCheck(requestDto.getAssigneeId()));
        }
    }
}
