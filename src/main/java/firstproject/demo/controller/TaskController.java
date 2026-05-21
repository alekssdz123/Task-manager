package firstproject.demo.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import firstproject.demo.model.Task;
import firstproject.demo.service.TaskService;

@RestController // creates JSON
@CrossOrigin(origins = "*")
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getAll(@RequestParam UUID userId, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " GET request: get all tasks");
        return service.getUserTasks(userId);
    }
    @PostMapping
    public Task addTask(@RequestBody Task task, HttpServletRequest request){ // Create Task object task from JSON
        String client = request.getRemoteAddr();
        logger.info(client + " POST request: add new task");
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
