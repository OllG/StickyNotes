package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.olpinski.stickynotes.data.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;

@Controller
public class ChangeMailController {

    private UserService userService;

    public ChangeMailController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/change-mail")
    public String getMailChange(Model model, Authentication authentication){
        Long id = (Long) authentication.getPrincipal();
        UserDto user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "edit-user/change-mail";
    }

    @PostMapping("/change-mail")
    public String postMailChange(@RequestParam("mail") String mail, Authentication authentication){
        Long id = (Long) authentication.getPrincipal();
        userService.setNewTempMail(id, mail);
        return "redirect:/notes";
    }

    @GetMapping("/confirm-mail")
    public String activateNewMail(@RequestParam("login") String login,
                                  @RequestParam("token") String token){

        if(token.equals(userService.getTokenByLogin(login))){
            userService.confirmNewMail(login);
            return "redirect:/notes";
        }
        throw new RuntimeException("Error while trying to change mail");
    }
}
