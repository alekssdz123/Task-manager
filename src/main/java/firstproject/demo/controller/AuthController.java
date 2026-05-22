package firstproject.demo.controller;

import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import firstproject.demo.model.User;
import firstproject.demo.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;        
    }
    @GetMapping("/register")
    public String userPage(HttpServletRequest request){
        return "register";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreationDate(LocalDate.now());
        userRepository.save(user);
        logger.info("New user " + user.getUserId() + " registered");
        return "redirect:/auth/register";
    }

}
