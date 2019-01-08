package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.data.dto.UserCreationDto;
import pl.olpinski.stickynotes.service.UserService;
import pl.olpinski.stickynotes.web.validation.NewUserDtoValidator;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private UserService userService;
    private NewUserDtoValidator newUserDtoValidator;

    public RegistrationController(UserService userService, NewUserDtoValidator newUserDtoValidator) {
        this.userService = userService;
        this.newUserDtoValidator = newUserDtoValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(newUserDtoValidator);
    }

    @GetMapping("/register")
    public String registerForm(UserCreationDto userCreationDto, Model model, Authentication authentication){

        if(authentication != null){
            return "redirect:/notes";
        }

        model.addAttribute("user", userCreationDto);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserCreationDto userCreationDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.registerNewUser(userCreationDto);
        return "redirect:/login/";
    }


    @GetMapping("/user/activate")
    public ModelAndView activateUser(@RequestParam("login") String login, @RequestParam("token") String token){
        boolean activated = userService.activateUser(login, token);
        return new ModelAndView("redirect:/login");
    }
}
