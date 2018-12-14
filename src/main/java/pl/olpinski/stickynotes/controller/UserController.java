package pl.olpinski.stickynotes.controller;

import org.springframework.stereotype.Controller;
import pl.olpinski.stickynotes.service.UserService;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

}
