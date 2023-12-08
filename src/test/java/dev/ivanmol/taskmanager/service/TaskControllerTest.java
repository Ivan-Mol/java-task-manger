package dev.ivanmol.taskmanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ivanmol.taskmanager.controller.TaskController;
import dev.ivanmol.taskmanager.dto.task.TaskDto;
import dev.ivanmol.taskmanager.model.task.Priority;
import dev.ivanmol.taskmanager.model.task.Status;
import lombok.SneakyThrows;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(SecurityConfig.class)
@WebMvcTest(value = TaskController.class)
public class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private TaskService taskServiceMock;

//    @SneakyThrows
//    @Test
//    void getTaskById() {
//        TaskDto responseDto = new TaskDto();
//        responseDto.setId(1L);
//        responseDto.setAssigneeId(2L);
//        responseDto.setName("test task");
//        responseDto.setPriority(Priority.LOW);
//        responseDto.setDescription("description test");
//        responseDto.setStatus(Status.NEW);
//        responseDto.setAuthorId(3L);
//
//        when(taskServiceMock.getById(1L)).thenReturn(responseDto);
//
//        mockMvc.perform(get("/tasks/{taskId}", responseDto.getId()))
//
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));
//    }
}
