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

        when(repo.save(any(Task.class))).then(invocation -> invocation.getArgument(0));

        Task result = service.addTask(task);

        assertNotNull(result);
        assertEquals("Test title", result.getTitle());
        assertFalse(result.getCompleteStatus());

        verify(repo, times(1)).save(any(Task.class));
    }

}
