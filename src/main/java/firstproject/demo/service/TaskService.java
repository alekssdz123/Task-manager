package firstproject.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import firstproject.demo.model.Task;
import firstproject.demo.exception.InvalidDataException;
import firstproject.demo.exception.NotFoundException;

@Service
public class TaskService {
    private ArrayList<Task> tasks;

    public TaskService(){
        this.tasks = new ArrayList<Task>();
    }

    public ArrayList<Task> getAllTasks(){
        return new ArrayList<>(this.tasks);
    }

    public ArrayList<Task> getUserTasks(String userId){
        ArrayList<Task> userTasks = new ArrayList<>();
        for(Task task : this.tasks){
            if(userId.equals(task.getUserId())){
                userTasks.add(task);
            }
        }
        return userTasks;
    }

    public Task addTask(Task task) {
        if(task.getTitle() == null || task.getTitle().isBlank()) {
            throw new InvalidDataException("Task name is not valid");
        }
        task.setId(UUID.randomUUID().toString());
        task.setCreationDate(LocalDate.now());
        task.setCompleteStatus(false);
        tasks.add(task);
        return task;
    }

    public Task getById(String taskId){
        for(Task task : this.tasks){
            if(taskId.equals(task.getId())){
                return task;
            }
        }
        throw new NotFoundException("Task not found");
    }

    public void deleteTask(String taskId){
        Task task = getById(taskId);
        tasks.remove(task);
    }
    
    public Task markAsCompleted(String taskId){
        Task task = getById(taskId);
        task.setCompleteStatus(true);
        return task;
    }
}

