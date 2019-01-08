package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.olpinski.stickynotes.data.dto.UserDetailsDto;
import pl.olpinski.stickynotes.data.dto.UserDto;
import pl.olpinski.stickynotes.data.entity.User;
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

    @GetMapping({"/notes", "", "/"})
    public String userNotes(Model model, Authentication authentication){
        Long id = (Long) authentication.getPrincipal();
        UserDto userDto = userService.findUserById(id);
        model.addAttribute("user", userDto);
        return "notes";
    }

    @GetMapping("/user/details")
    public String userDetails(Authentication authentication, Model model){
        Long id = (Long) authentication.getPrincipal();
        UserDetailsDto user = userService.getDetails(id);
        model.addAttribute("user", user);
        return "user_details";
    }

    @GetMapping("reset-password")
    public String resetPasswordForm(){
        return "reset-password";
    }

    @PostMapping("reset-password")
    public String resetConfirmation(@RequestParam String mail){
        userService.resetPasswordAttempt(mail);
        return "redirect:/login";
    }

    @GetMapping("new-password")
    public String resettingPassword(@RequestParam("login") String login, @RequestParam("token") String token, Model model){
        String mail = userService.findUserByLogin(login).getMail();
        User user = userService.findUserByMail(mail);

        if(token.equals(user.getToken())){
            model.addAttribute("login", login);
            model.addAttribute("token", token);
            return "new-password";
        }
        else throw new RuntimeException("Error while trying to reset password");
    }

    @PostMapping("new-password")
    public String settingNewPassword(@RequestParam("token") String token, @RequestParam("login") String login, @RequestParam("password") String password){
        String mail = userService.findUserByLogin(login).getMail();
        User user = userService.findUserByMail(mail);

        if(token.equals(user.getToken())){

            userService.changeUserPassword(login, token, password);
            return "redirect:/login";
        }
        else throw new RuntimeException("Error while changing password");
    }
}
