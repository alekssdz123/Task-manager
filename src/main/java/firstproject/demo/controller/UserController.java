package firstproject.demo.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import firstproject.demo.service.UserService;
import firstproject.demo.model.User;

@RestController
@RequestMapping("/auth/register")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public void addUser(@RequestBody User user, HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info("POST request: add new user");
    }

    @PutMapping
    public void updateUser(HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info("PUT request: update user");
    }

    @DeleteMapping
    public void deleteUser(HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info("DELETE request: delete user");
    }
}
