package firstproject.demo.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import firstproject.demo.exception.InvalidDataException;
import firstproject.demo.exception.NotFoundException;
import firstproject.demo.model.User;

@Service
public class UserService {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
    private ArrayList<User> users;

    public UserService(){
        this.users = new ArrayList<>();
    }

    public User getUserById(UUID userId){
        for(User user : this.users){
            if(userId.equals(user.getUserId())){
                return user;
            }
        }
        throw new NotFoundException("User not found");
    }

    public void validateUserData(User user){
        if(user.getUsername() == null || user.getUsername().isBlank()){
            throw new InvalidDataException("Username can not be empty");
        }
        if(user.getUsername().length() < 3 || user.getUsername().length() > 30){
            throw new InvalidDataException("Username must be 3 - 30 charachters long");
        }

        if(user.getPassword() == null || user.getPassword().isBlank()){
            throw new InvalidDataException("Password can not be empty");
        }
        if(!user.getPassword().matches(PASSWORD_REGEX)){
            throw new InvalidDataException("Invalid password format");
        }

        if(user.getEmail() == null || user.getEmail().isBlank()){
            throw new InvalidDataException("Email can not be empty");
        }
        if(user.getEmail().length() < 6){
            throw new InvalidDataException("Email must be at least 6 characters");
        }
        if(!user.getEmail().matches(EMAIL_REGEX)){
            throw new InvalidDataException("Invalid email format");
        }
    }

    public void checkIfUserExists(User user){
        for(User u : this.users){
            if(user.getUsername().equals(u.getUsername())){
                throw new InvalidDataException("Username already exists");
            }
            if(user.getEmail().equals(u.getEmail())){
                throw new InvalidDataException("Email already exists");
            }
        }
    }
    
    public void normalizeData(User user){
        user.setUsername(user.getUsername().trim());
        user.setEmail((user.getEmail().trim()).toLowerCase());
    }
    
    public void registerUser(User user){
        validateUserData(user);
        normalizeData(user);
        checkIfUserExists(user);
        user.setUserId(UUID.randomUUID());
        this.users.add(user);
    }

    public void deleteUser(UUID userId){
        User user = getUserById(userId);
        this.users.remove(user);
    }

    public User loginUser(String username, String password){
        username = username.trim();
        for(User user : this.users){
            if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
                User safeUser = new User(user);
                safeUser.setPassword(null); // So we dont send password to frontend 
                return safeUser;
            }
        }
        throw new InvalidDataException("Invalid username or password");
    }
}
