package pl.olpinski.stickynotes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.exception.LoginTakenException;
import pl.olpinski.stickynotes.exception.MailTakenException;
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
    public String registerForm(@RequestParam(required = false) String loginError,@RequestParam(required = false) String mailError, Model model){
        model.addAttribute("loginError", loginError);
        model.addAttribute("mailError", mailError);
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(NewUserDto newUserDto/*@RequestParam String login, @RequestParam String password, @RequestParam String mail*/){

        try {
            userService.registerNewUser(newUserDto);
        } catch (LoginTakenException e){
          /*bindingResult.rejectValue("login", "login already taken");
            return new ModelAndView("/register");*/
            //e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("redirect:/register");
            modelAndView.addObject("loginError", "login");
            return modelAndView;
        } catch (MailTakenException e){
            //e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("redirect:/register");
            modelAndView.addObject("mailError", "mail");
            return modelAndView;
        }
        return new ModelAndView("redirect:/notes/");
    }
}
