package pl.olpinski.stickynotes.web.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.service.UserService;

import java.util.ArrayList;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public DatabaseAuthenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        System.out.println(passwordEncoder);

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println(authentication.getCredentials());
        //System.out.println(passwordEncoder.encode(authentication.getCredentials().toString()));
        //System.out.println(passwordEncoder.encode(authentication.getCredentials().toString()));

        if(userService.authenticate(login, password)){

            UserDto userDto = userService.findUserByLogin(login);
            Long id = userDto.getId();

            return new UsernamePasswordAuthenticationToken(
                    id, password, new ArrayList<>());
        }

        else {
            System.out.println("Coś nie tak przy logowaniu");
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
