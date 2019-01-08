package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.data.dto.NewUserDto;
import pl.olpinski.stickynotes.service.UserService;
import pl.olpinski.stickynotes.web.validation.NewUserDtoValidator;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;
    private NewUserDtoValidator newUserDtoValidator;

    public UserController(UserService userService, NewUserDtoValidator newUserDtoValidator) {
        this.userService = userService;
        this.newUserDtoValidator = newUserDtoValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newUserDtoValidator);
    }

    @GetMapping("/login")
    public String loginForm(Authentication authentication) {

        if (authentication != null) {
            return "redirect:/notes/";
        }
        return "login";
    }

    @GetMapping("/register")
    // NewUserDto zamieniłbym na UserDto
    public String registerForm(NewUserDto newUserDto, Model model, Authentication authentication) {

        if (authentication != null) {
            return "redirect:/notes";
        }

        model.addAttribute("user", newUserDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid NewUserDto newUserDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerNewUser(newUserDto);
        return "redirect:/login/";
    }

    @GetMapping("/user/activate")
    public ModelAndView activateUser(@RequestParam("login") String login, @RequestParam("token") String token) {
        boolean activated = userService.activateUser(login, token);
        return new ModelAndView("redirect:/login");
    }
}
