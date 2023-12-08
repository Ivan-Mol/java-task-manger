package dev.ivanmol.taskmanager.service;

import dev.ivanmol.taskmanager.exception.NotFoundException;
import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import dev.ivanmol.taskmanager.model.task.Task;
import dev.ivanmol.taskmanager.model.user.User;
import dev.ivanmol.taskmanager.repository.TaskRepository;
import dev.ivanmol.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase
class TaskRepositoryTest {
    User createdUser1;
    User createdUser2;
    Task createdTask1;
    Task createdTask2;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        User user1 = new User();
        user1.setName("user1");
        user1.setEmail("user1@mail.com");
        user1.setPassword("soSecretKey");
        createdUser1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("user2");
        user2.setEmail("user2@mail.com");
        user2.setPassword("soSecretKey2");
        createdUser2 = userRepository.save(user2);

        Task task1 = new Task();
        task1.setName("testTask1");
        task1.setDescription("testDescription");
        task1.setStatus(Status.NEW);
        task1.setAuthor(createdUser1);
        task1.setPriority(Priority.LOW);
        createdTask1 = taskRepository.save(task1);

        Task task2 = new Task();
        task2.setName("testTask2");
        task2.setDescription("testDescription2");
        task2.setStatus(Status.NEW);
        task2.setAuthor(createdUser2);
        task2.setPriority(Priority.LOW);
        task2.setAssignee(createdUser2);
        createdTask2 = taskRepository.save(task2);
    }

    @Test
    void getByIdAndCheck_IsTaskValid() {
        assertEquals(createdTask1, taskRepository.getByIdAndCheck(createdTask1.getId()));
    }

    @Test
    void getByIdAndCheck_IsTaskInvalid() {
        assertThrows(NotFoundException.class, () -> taskRepository.getByIdAndCheck(createdTask1.getId() + 333));
    }


    @Test
    void getAllByAssignee_withCorrectAssignee() {
        List<Task> fromDb = taskRepository.getAllByAssignee(createdUser2);
        assertTrue(fromDb.contains(createdTask2));
    }

    @Test
    void getAllByAuthor_withCorrectAuthor() {
        List<Task> tasks = taskRepository.getByAuthor(createdUser1);
        assertTrue(tasks.contains(createdTask1));
    }

    @AfterEach
    void afterEach() {
        taskRepository.deleteById(createdTask1.getId());
        taskRepository.deleteById(createdTask2.getId());
        userRepository.deleteById(createdUser1.getId());
        userRepository.deleteById(createdUser2.getId());
    }
}