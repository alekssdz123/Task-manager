package firstproject.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @GetMapping("/login")
    public static String loginPage(HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " GET request: get login page");
        return "login";
    }

    @GetMapping("/registration")
    public static String regPage(HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " GET request: get registration page");
        return "register";
    }

    @GetMapping("/")
    public static String tasksPage(HttpServletRequest request){
        String client = request.getRemoteAddr();
        logger.info(client + " GET request: get tasks page");
        return "tasks";
    }
}
