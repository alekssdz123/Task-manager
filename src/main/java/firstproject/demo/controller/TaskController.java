package firstproject.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Task> getAll(@RequestParam String userId){
        logger.info("GET request: get all tasks");
        logger.info("GET request: userId: " + userId);
        return service.getUserTasks(userId);
    }
    @PostMapping
    public Task addTask(@RequestBody Task task){ // Create Task object task from JSON
        logger.info("POST request: add new task");
        Task savedTask = service.addTask(task);
        System.out.println(savedTask);
        return savedTask;
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id){
        logger.info("GET request: get task by id");
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskById(@PathVariable String id){
        logger.info("DELETE request: delete task by id");
        service.deleteTask(id);
    }
}
