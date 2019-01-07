package pl.olpinski.stickynotes.service;


import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;

public interface UserService {

    UserDto findUserById(Long id);
    UserDto findUserByLogin(String login);
    boolean authenticate(String login, String password);

    User registerNewUser (NewUserDto newUserDto);
    boolean activateUser(String login, String token);

    boolean isMailRegistered(String mail);
    boolean isLoginTaken(String login);

}
