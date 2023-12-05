package dev.ivanmol.taskmanager.service.impl;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.TaskShortDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.exception.ValidationException;
import dev.ivanmol.taskmanager.mapper.TasksMapper;
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
import java.util.stream.Collectors;

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
        isTaskValid(updatedTask);
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
    public Collection<TaskShortDto> getAllTasks(List<Long> tasksIds, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (tasksIds == null || tasksIds.isEmpty()) {
            return taskRepository.findAll(pageable)
                    .stream()
                    .map(TasksMapper::toShortTaskDto)
                    .collect(Collectors.toList());
        } else {
            return taskRepository.getAllByIdInOrderByIdDesc(tasksIds, pageable)
                    .stream()
                    .map(TasksMapper::toShortTaskDto)
                    .collect(Collectors.toList());
        }
    }

    private void isUserTaskAuthor(User user, Task task) {
        if (!user.getId().equals(task.getAuthor().getId())) {
            throw new ValidationException("User with id " + user.getId() + " is not task owner: " + task.getId());
        }
    }

    private void isTaskValid(Task task) {
        String name = task.getName();
        if (name.length() < 2 || name.length() > 70) {
            throw new ValidationException("Name is shorter than 2 or longer than 70");
        }
        String description = task.getDescription();
        if (description.length() < 2 || description.length() > 3000) {
            throw new ValidationException("Description is shorter than 2 or longer than 3000");
        }
        if(task.getAssignee()!=null){
            userRepository.getByIdAndCheck(task.getAssignee().getId());
            if (task.getAuthor().getId().equals(task.getAssignee().getId())) {
                throw new ValidationException("Author can't be a performer");
            }
        }
    }

    private void checkAndSetAssigneeToTask(UpdateTaskRequestDto requestDto, Task taskFromDb) {
        if (requestDto.getAssigneeId()!=null){
            taskFromDb.setAssignee(userRepository.getByIdAndCheck(requestDto.getAssigneeId()));
        }
    }
}
