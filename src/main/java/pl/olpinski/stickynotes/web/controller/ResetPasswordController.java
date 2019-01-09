package pl.olpinski.stickynotes.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.olpinski.stickynotes.data.dto.PasswordResetDto;
import pl.olpinski.stickynotes.service.UserService;

@Controller
public class ResetPasswordController {

    private UserService userService;

    public ResetPasswordController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("reset-password")
    public String resetPasswordForm(){
        return "edit-user/reset-password";
    }

    @PostMapping("reset-password")
    public String resetConfirmation(@RequestParam String mail){
        userService.resetPasswordAttempt(mail);
        return "redirect:/login";
    }

    @GetMapping("new-password")
    public String resettingPassword(@RequestParam("login") String login,
                                    @RequestParam("token") String token,
                                    Model model){
        PasswordResetDto user = userService.resetPasswordDto(login);

        if(token.equals(user.getToken())){
            model.addAttribute("login", login);
            model.addAttribute("token", token);
            return "edit-user/new-password";
        }
        else throw new RuntimeException("Error while trying to reset password");
    }

    @PostMapping("new-password")
    public String settingNewPassword(@RequestParam("token") String token,
                                     @RequestParam("login") String login,
                                     @RequestParam("password") String password){

        PasswordResetDto user = userService.resetPasswordDto(login);

        if(token.equals(user.getToken())){
            userService.changeUserPassword(login, token, password);
            return "redirect:/login";
        }
        else throw new RuntimeException("Error while changing password");
    }
}
