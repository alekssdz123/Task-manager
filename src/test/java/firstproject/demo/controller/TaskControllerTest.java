package firstproject.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import firstproject.demo.model.Task;
import firstproject.demo.model.User;
import firstproject.demo.repository.TaskRepository;
import firstproject.demo.repository.UserRepository;
import firstproject.demo.security.SecurityConfig;
import firstproject.demo.service.TaskService;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = true)
@Import(SecurityConfig.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService service;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private TaskRepository taskRepository;
    
    final private String apiUrl = "/api/tasks";

    @Test
    public void testGetAll() throws Exception{
        User user = new User(UUID.randomUUID(), "testUser", "password123", "example@gmail.com");
        when(userRepository.findByUsername("testUser"))
            .thenReturn(Optional.of(user));

        mockMvc.perform(get(apiUrl).with(user("testUser").roles("USER")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testAddTask() throws Exception{
        User user = new User(UUID.randomUUID(), "testUser", "password123", "example@gmail.com");
        Task task = new Task();
        task.setTitle("TestTask");
        when(userRepository.findByUsername("testUser"))
            .thenReturn(Optional.of(user));
            
        when(service.addTask(any(Task.class)))
            .thenAnswer(invocation -> {
                Task t = invocation.getArgument(0);
                t.setId(UUID.randomUUID());
                return t;
            });

        mockMvc.perform(post(apiUrl)
                .with(user("testUser").roles("USER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(task)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("TestTask"));
        
        verify(service).addTask(any(Task.class));
    }

    @Test
       void testGetTaskById() throws Exception {

           UUID id = UUID.randomUUID();

           Task task = new Task();
           task.setId(id);
           task.setTitle("Task1");

           when(service.getById(id)).thenReturn(task);

           mockMvc.perform(get(apiUrl + "/" + id)
                           .with(user("testUser").roles("USER")))
                   .andExpect(status().isOk())
                   .andExpect(jsonPath("$.id").value(id.toString()))
                   .andExpect(jsonPath("$.title").value("Task1"));

           verify(service).getById(id);
       }

    @Test
        void testDeleteTaskById() throws Exception {

            UUID id = UUID.randomUUID();

            mockMvc.perform(delete(apiUrl + "/" + id)
                            .with(user("testUser").roles("USER")))
                    .andExpect(status().isOk());

            verify(service).deleteTask(id);
        }


    @Test
    public void testMarkTaskAsComplete() throws Exception{
        UUID id = UUID.randomUUID();

        mockMvc.perform(put(apiUrl + "/" + id + "/complete")
                        .with(user("testUser").roles("USER")))
                .andExpect(status().isOk());

        verify(service).markAsCompleted(id);
    }

    @Test
    public void testUpdateTask() throws Exception{
        UUID id = UUID.randomUUID();

        Task task = new Task();
        task.setTitle("Updated Task");

        mockMvc.perform(put(apiUrl + "/" + id)
                        .with(user("testUser").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isOk());

        verify(service).updateTask(eq(id), any(Task.class));
    }
}
