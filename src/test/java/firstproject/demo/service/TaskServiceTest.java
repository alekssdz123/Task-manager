package firstproject.demo.service;

import firstproject.demo.model.Task;
import firstproject.demo.repository.TaskRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository repo;
    
    @InjectMocks
    private TaskService service;
    
    @Test
    void testAddTask(){
        Task task = new Task();
        task.setTitle("Test title");
        task.setTaskDescription("Test description");

        when(repo.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Task result = service.addTask(task);

        assertNotNull(result);
        assertEquals("Test title", result.getTitle());
        assertFalse(result.getCompleteStatus());

        verify(repo, times(1)).save(any(Task.class));
    }

    @Test
    void testGetById(){
        Task task = new Task();
        task.setId(UUID.randomUUID());

        when(repo.findById(task.getId())).thenReturn(Optional.of(task));

        Task result = service.getById(task.getId());

        assertEquals(task.getId(), result.getId());
    }

    @Test
    void testDeleteTask(){
        Task task = new Task();
        task.setId(UUID.randomUUID());

        doNothing().when(repo).deleteById(task.getId());

        service.deleteTask(task.getId());

        verify(repo, times(1)).deleteById(task.getId());

    }

    @Test
    void testMarkAsCompleted(){
        Task task = new Task();
        task.setId(UUID.randomUUID());
        task.setCompleteStatus(false);

        doNothing().when(repo).markAsCompleted(task.getId());

        service.markAsCompleted(task.getId());

        verify(repo, times(1)).markAsCompleted(task.getId());
    }

}
