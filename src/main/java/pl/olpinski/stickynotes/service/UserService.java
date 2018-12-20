package pl.olpinski.stickynotes.service;

import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.dto.UserDto;

public interface UserService {

    UserDto findUserById(Long id);
    UserDto findUserByLogin(String login);
    boolean authenticate(String login, String password);

}
