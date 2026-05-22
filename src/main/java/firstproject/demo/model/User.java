package firstproject.demo.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String username;
    private String password;
    private String email;
    private LocalDate createdAt;

    public User(){

    }

    public User(UUID userId, String username, String password, String email){
        this.id = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = LocalDate.now();
    }

    public User(User otherUser){
        this.id = otherUser.getUserId();
        this.username = otherUser.getUsername();
        this.password = otherUser.getPassword();
        this.email = otherUser.getEmail();
        this.createdAt = otherUser.getCreationDate();
    }

    public UUID getUserId(){
        return this.id;
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

    public void setUserId(UUID userId){
        this.id = userId;
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
