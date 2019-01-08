package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.olpinski.stickynotes.data.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Authentication authentication){

        if(authentication != null){
            return "redirect:/notes/";
        }
        return "login";
    }

    @GetMapping("/user/details")
    public String userDetails(Authentication authentication, Model model){
        Long id = (Long) authentication.getPrincipal();
        UserDto user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user_details";
    }

    @GetMapping({"/notes", "", "/"})
    public String user(Model model, Authentication authentication){

        Long id = (Long) authentication.getPrincipal();
        UserDto userDto = userService.findUserById(id);
        model.addAttribute("user", userDto);
        return "notes";
    }
}
