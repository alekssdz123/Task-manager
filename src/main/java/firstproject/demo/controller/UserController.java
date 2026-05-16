package firstproject.demo.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import firstproject.demo.service.UserService;
import firstproject.demo.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public void addUser(@RequestBody User user){
        logger.info("POST request: add new user");
    }

    @PutMapping
    public void updateUser(){
        logger.info("PUT request: update user");
    }

    @DeleteMapping
    public void deleteUser(){
        logger.info("DELETE request: delete user");
    }
}
