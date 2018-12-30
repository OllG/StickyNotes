package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/notes", "", "/"})
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
    public String registerForm(NewUserDto newUserDto, Model model){

        model.addAttribute("user", newUserDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid NewUserDto newUserDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerNewUser(newUserDto);
        return "redirect:/login/";
    }

    @GetMapping("/user/activate")
    public ModelAndView activateUser(@RequestParam("login") String login, @RequestParam("token") String token){
        boolean activated = userService.activateUser(login, token);
        return new ModelAndView("redirect:/login");
    }
}
