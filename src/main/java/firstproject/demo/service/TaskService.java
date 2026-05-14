package firstproject.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import firstproject.demo.model.Task;
import firstproject.demo.repository.TaskRepository;
import firstproject.demo.exception.InvalidDataException;
import firstproject.demo.exception.NotFoundException;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo){
        this.repo = repo;
    }

    public List<Task> getAllTasks(){
        return repo.findAll();
    }

    public List<Task> getUserTasks(UUID userId){
        return repo.findByUserIdOrderByCompletedAsc(userId);
    }

    public Task addTask(Task task) {
        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new InvalidDataException("Task name is not valid");
        }
        if(task.getTitle().length() > 100){
            throw new InvalidDataException("Task name is too long");
        }
        if(task.getDescription().length() > 1000){
            throw new InvalidDataException("Task description is too long");
        }
        task.setCreationDate(LocalDate.now());
        task.setCompleteStatus(false);
        repo.save(task);
        return task;
    }

    public Task getById(UUID taskId){
        Task task = repo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
        return task;
    }

    public void deleteTask(UUID taskId){
        repo.deleteById(taskId);
    }
    
    public void markAsCompleted(UUID taskId){
        repo.markAsCompleted(taskId);
    }

    public void updateTask(UUID id, Task newTask){
        Task task = getById(id);
        task.setTitle(newTask.getTitle());
        task.setTaskDescription(newTask.getDescription());
        repo.save(task);
    }
}

