package pl.olpinski.stickynotes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.service.UserService;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{user_id}")
    public String user(Model model, @PathVariable("user_id") Long id){
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/login")
    public String loginForm(){

        return "login";
    }

    @PostMapping("/login")
    public String loginAttempt(@RequestParam("login") String login, @RequestParam("password") String password){

        return "login";
    }
}
