package firstproject.demo.model;

import java.time.LocalDate;

public class User {
    private String userId;
    private String username;
    private String password;
    private String email;
    private LocalDate createdAt;

    public User(String userId, String username, String password, String email){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDate.now();
    }

    public String getUserId(){
        return this.userId;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public LocalDate getCreationDate(){
        return this.createdAt;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String pawssword){
        this.password = pawssword;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setCreationDate(LocalDate createdAt){
        this.createdAt = createdAt;
    }
}
