package pl.olpinski.stickynotes.web.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.data.entity.UserStatus;
import pl.olpinski.stickynotes.data.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;
import pl.olpinski.stickynotes.web.exceptions.DisabledUserException;
import pl.olpinski.stickynotes.web.exceptions.NotActivatedUserException;

import java.util.ArrayList;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;

    public DatabaseAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(userService.authenticate(login, password)){

            UserDto userDto = userService.findUserByLogin(login);
            Long id = userDto.getId();
            if(userService.checkStatus(id).equals(UserStatus.NEW)){
                throw new NotActivatedUserException("not activated user");
            } else if (userService.checkStatus(id).equals(UserStatus.DISABLED)){
                throw new DisabledUserException("disabled user");
            }
            //Success
            else {
                return new UsernamePasswordAuthenticationToken(
                        id, password, new ArrayList<>());
            }
        } else {
            System.out.println("Error while logging");
            throw new BadCredentialsException("bad credentials");
            //return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
