package firstproject.demo.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import firstproject.demo.exception.NotFoundException;
import firstproject.demo.model.Task;
import firstproject.demo.model.User;
import firstproject.demo.repository.UserRepository;
import firstproject.demo.service.TaskService;

@RestController // creates JSON
@CrossOrigin(origins = "*")
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    private final UserRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping
    public List<Task> getAll(Authentication auth, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " GET request: get all tasks");
        
        String username = auth.getName();
        User user = repository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        
        UUID userId = user.getUserId();

        return service.getUserTasks(userId);
    }
    @PostMapping
    public Task addTask(@RequestBody Task task, Authentication auth, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " POST request: add new task");

        String username = auth.getName();
        User user = repository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found"));
        UUID userId = user.getUserId();

        task.setUserId(userId);
        Task savedTask = service.addTask(task);
        return savedTask;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " GET request: get task by id");
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable UUID id, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " DELETE request: delete task by id");
        service.deleteTask(id);
    }

    @PutMapping("/{id}/complete")
    public void markTaskAsComplete(@PathVariable UUID id, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " PUT REQUEST: mark task as completed");
        service.markAsCompleted(id);
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable UUID id, @RequestBody Task task, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " PUT REQUEST: update task " + id);
        service.updateTask(id, task);
    }
}
