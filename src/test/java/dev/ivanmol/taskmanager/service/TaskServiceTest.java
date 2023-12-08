package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.dto.task.NewTaskRequestDto;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.dto.task.UpdateTaskRequestDto;
import dev.ivanmol.taskmanager.dto.user.NewUserRequestDto;
import dev.ivanmol.taskmanager.exception.NotFoundException;
import dev.ivanmol.taskmanager.mapper.UsersMapper;
import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.repository.UserRepository;
import dev.ivanmol.taskmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskServiceImpl taskService;
    NewUserRequestDto userRequestDto;
    User userWithId;
    NewTaskRequestDto taskRequestDto;
    TaskDto taskDtoWithId;


    @BeforeEach
    void beforeAll() {
        userRequestDto = new NewUserRequestDto();
        userRequestDto.setName("ivan");
        userRequestDto.setEmail("ivanov@gmail.com");
        userRequestDto.setPassword("secretKey");
        userWithId = userRepository.save(UsersMapper.toUser(userRequestDto));
        taskRequestDto = new NewTaskRequestDto();
        taskRequestDto.setName("Test Task");
        taskRequestDto.setDescription("Description for test task");
        taskRequestDto.setPriority(Priority.LOW);
        taskDtoWithId = taskService.createTask(userWithId.getId(), taskRequestDto);
    }

    @Test
    void taskMustBeCreated() {
        Assertions.assertNotNull(taskDtoWithId.getId());
        Assertions.assertEquals(taskDtoWithId.getName(), taskRequestDto.getName());
        Assertions.assertEquals(taskDtoWithId.getPriority(), taskRequestDto.getPriority());
        Assertions.assertEquals(taskDtoWithId.getDescription(), taskRequestDto.getDescription());
        Assertions.assertEquals(taskDtoWithId.getStatus(), Status.NEW);

    }

    @Test
    void taskNameMustBeUpdated() {
        UpdateTaskRequestDto updateDto = new UpdateTaskRequestDto();
        updateDto.setName("test name");
        TaskDto updatedDto = taskService.updateTaskByAuthor(userWithId.getId(), taskDtoWithId.getId(), updateDto);
        Assertions.assertEquals(updateDto.getName(), updatedDto.getName());
    }

    @Test
    void getByAuthorShouldThrowAnException() {
        Assertions.assertThrows(NotFoundException.class, () -> taskService.getByAuthor(userWithId.getId() + 333));
    }

    @Test
    void getTasksByAssigneeShouldThrowAnException() {
        Assertions.assertThrows(NotFoundException.class, () -> taskService.getTasksByAssignee(userWithId.getId() + 333));
    }

    @AfterEach
    void afterEach() {
        taskService.deleteTaskByAuthor(userWithId.getId(), taskDtoWithId.getId());
        userRepository.delete(userWithId);
    }
}
