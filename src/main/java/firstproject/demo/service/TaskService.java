package firstproject.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import firstproject.demo.model.Task;

@Service
public class TaskService {
    private ArrayList<Task> tasks;

    public TaskService(){
        this.tasks = new ArrayList<Task>();
    }

    public ArrayList<Task> getAllTasks(){
        return this.tasks;
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
        task.setId(UUID.randomUUID().toString());
        task.setCreationDate(LocalDate.now());
        task.setCompleteStatus(false);
        tasks.add(task);
        return task;
    }

    public void deleteTask(String taskId){
        tasks.removeIf(task -> taskId.equals(task.getId()));
    }

    public Task getById(String taskId){
        for(Task task : this.tasks){
            if(task.getId().equals(taskId)){
                return task;
            }
        }
        return null;
    }
}
