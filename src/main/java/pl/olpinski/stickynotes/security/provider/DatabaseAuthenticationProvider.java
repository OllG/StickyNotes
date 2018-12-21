package pl.olpinski.stickynotes.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;

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
        String password = DigestUtils.md5DigestAsHex(authentication.getCredentials().toString().getBytes());

        if(userService.authenticate(login, password)){

            //Dodawać do sesji userDTO może
            UserDto userDto = userService.findUserByLogin(login);
            Long id = userDto.getId();

            return new UsernamePasswordAuthenticationToken(
                    id, password, new ArrayList<>());
        }
        else return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
