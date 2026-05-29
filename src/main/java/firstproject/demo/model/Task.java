package firstproject.demo.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String title;
    private String taskDescription;
    private boolean completed;
    private LocalDate createdAt;

    public Task(){

    }

    public Task(Task otherTask){
        this.id = otherTask.getId();
        this.user = otherTask.getUser();
        this.title = otherTask.getTitle();
        this.taskDescription = otherTask.getDescription();
        this.completed = otherTask.getCompleteStatus();
        this.createdAt = otherTask.getCreationDate();
    }

    @Override
    public String toString() {
        return this.id.toString() + ", " + this.user.toString() +  ", " + this.title + ", " + this.taskDescription + ", " + String.valueOf(this.completed) + ", " + this.createdAt.toString();
    }

    public UUID getId(){
        return this.id;
    }
    public User getUser(){
        return this.user;
    }
    public String getTitle(){
        return this.title;
    }
    public String getDescription(){
        return this.taskDescription;
    }
    public boolean getCompleteStatus(){
        return this.completed;
    }
    public LocalDate getCreationDate(){
        return this.createdAt;
    }

    public void setId(UUID id){
        this.id = id;
    }
    public void setUser(User user){
        this.user = user;
    }
    public void setTitle(String taskTitle){
        this.title = taskTitle;
    }
    public void setTaskDescription(String taskDescription){
        this.taskDescription = taskDescription;
    }
    public void setCompleteStatus(boolean completionStatus){
        this.completed = completionStatus;
    }
    public void setCreationDate(LocalDate createdAt){
        this.createdAt = createdAt;
    }
}