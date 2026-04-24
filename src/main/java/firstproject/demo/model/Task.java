package firstproject.demo.model;

import java.time.LocalDate;

public class Task {
    private String id;
    private String userId;
    private String title;
    private String taskDescription;
    private boolean completed;
    private LocalDate createdAt;

    public Task(){

    }

    @Override
    public String toString() {
        return this.id + ", " + this.userId +  ", " + this.title + ", " + this.taskDescription + ", " + String.valueOf(this.completed) + ", " + this.createdAt.toString();
    }

    public String getId(){
        return this.id;
    }
    public String getUserId(){
        return this.userId;
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

    public void setId(String id){
        this.id = id;
    }
    public void setUserId(String userId){
        this.userId = userId;
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