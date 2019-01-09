package pl.olpinski.stickynotes.service;


import pl.olpinski.stickynotes.data.dto.PasswordResetDto;
import pl.olpinski.stickynotes.data.dto.UserDetailsDto;
import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.data.entity.UserStatus;
import pl.olpinski.stickynotes.data.dto.UserCreationDto;
import pl.olpinski.stickynotes.data.dto.UserDto;

public interface UserService {

    UserDto findUserById(Long id);
    UserDto findUserByLogin(String login);
    UserDetailsDto getDetails(Long id);

    boolean changeUserPassword(String login, String token, String password);

    void setNewTempMail(Long id, String mail);

    String getTokenByLogin(String login);
    void confirmNewMail(String login);

    PasswordResetDto resetPasswordDto(String mail);

    User findUserByMail(String mail);
    void resetPasswordAttempt(String mail);

    boolean authenticate(String login, String password);
    UserStatus checkStatus(Long id);

    User registerNewUser (UserCreationDto userCreationDto);
    boolean activateUser(String login, String token);

    boolean isMailRegistered(String mail);
    boolean isLoginTaken(String login);

}
