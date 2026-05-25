package firstproject.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import firstproject.demo.model.Task;
import firstproject.demo.repository.TaskRepository;
import firstproject.demo.exception.DataBaseException;
import firstproject.demo.exception.InvalidDataException;
import firstproject.demo.exception.NotFoundException;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo){
        this.repo = repo;
    }

    public List<Task> getAllTasks(){
        try{
            return repo.findAll();
        } catch(Exception e){
            throw new DataBaseException("Failed to get all tasks");
        }
    }

    public List<Task> getUserTasks(UUID userId){
        try{
            return repo.findByUserIdOrderByCompletedAsc(userId);
        } catch(Exception e){
            throw new DataBaseException("Failed to get user tasks");
        }
    }

    public void validateTask(Task task){
        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new InvalidDataException("Task name is not valid");
        }
        if(task.getTitle().length() > 100){
            throw new InvalidDataException("Task name is too long");
        }
        if(task.getDescription().length() > 1000){
            throw new InvalidDataException("Task description is too long");
        }
    }

    public Task addTask(Task task) {
        validateTask(task);
        try{
            task.setCreationDate(LocalDate.now());
            task.setCompleteStatus(false);
            repo.save(task);
            return task;
        } catch(Exception e){
            throw new DataBaseException("Failed to save new task : " + task.toString());
        }
    }

    public Task getById(UUID taskId){
        try{
            Task task = repo.findById(taskId).orElseThrow(() -> new NotFoundException("Task not found"));
            return task;
        } catch(Exception e){
            throw new DataBaseException("Failed to get task by id");
        }
    }

    public void deleteTask(UUID taskId){
        try{
            repo.deleteById(taskId);
        } catch(Exception e){
            throw new DataBaseException("Failed to delete task");
        }
    }
    
    public void markAsCompleted(UUID taskId){
        try{
            repo.markAsCompleted(taskId);
        } catch(Exception e){
            throw new DataBaseException("Failed to mark task as completed");
        }
    }

    public void updateTask(UUID id, Task newTask){
        try{
            Task task = getById(id);
            task.setTitle(newTask.getTitle());
            task.setTaskDescription(newTask.getDescription());
            repo.save(task);
        } catch(Exception e){
            throw new DataBaseException("Failed to update task");
        }
    }
}

