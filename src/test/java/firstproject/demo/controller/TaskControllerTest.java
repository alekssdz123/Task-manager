package firstproject.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import firstproject.demo.model.User;
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
}
