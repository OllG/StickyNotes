package pl.olpinski.stickynotes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.exception.LoginTakenException;
import pl.olpinski.stickynotes.exception.MailTakenException;
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
    public String registerForm(@RequestParam(required = false) String loginError,@RequestParam(required = false) String mailError, Model model){

        model.addAttribute("user", new UserDto());

        model.addAttribute("loginError", loginError);
        model.addAttribute("mailError", mailError);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Validated NewUserDto newUserDto, BindingResult bindingResult, Model model){

        model.addAttribute("user", newUserDto);

        if (bindingResult.hasErrors()) {
            return "customers/register";
        }

        try {
            userService.registerNewUser(newUserDto);
        } catch (LoginTakenException e){

            bindingResult.rejectValue("login", "login already taken");

            e.printStackTrace();

            return "register";

            /*ModelAndView modelAndView = new ModelAndView("redirect:/register");
            modelAndView.addObject("loginError", "login");
            return modelAndView;*/
        } catch (MailTakenException e){
            //e.printStackTrace();
            ModelAndView modelAndView = new ModelAndView("redirect:/register");
            modelAndView.addObject("mailError", "mail");
            return "register";
        }
        return "redirect:/notes/";
    }

    @GetMapping("/user/activate")
    public ModelAndView activateUser(@RequestParam("login") String login, @RequestParam("token") String token){
        boolean activated = userService.activateUser(login, token);

        return new ModelAndView("redirect:/login");
    }
}
