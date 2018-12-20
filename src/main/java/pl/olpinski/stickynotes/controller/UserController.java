package pl.olpinski.stickynotes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;

@Controller
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/notes")
    public String user(Model model, Authentication authentication){

        Long id = (Long) authentication.getPrincipal();
        UserDto userDto = userService.findUserById(id);
        model.addAttribute("user", userDto);
        return "notes";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@RequestParam String login, @RequestParam String password, @RequestParam String mail){
        UserDto userDto = new UserDto();
        userDto.setLogin(login);
        userDto.setPassword(password);
        userDto.setMail(mail);

        userService.registerNewUser(userDto);

        return new ModelAndView("redirect:/notes/");
    }
}
