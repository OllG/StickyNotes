package pl.olpinski.stickynotes.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.olpinski.stickynotes.converter.UserConverter;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.domain.UserStatus;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.exception.LoginTakenException;
import pl.olpinski.stickynotes.exception.MailTakenException;
import pl.olpinski.stickynotes.repository.UserRepository;
import pl.olpinski.stickynotes.service.MailService;
import pl.olpinski.stickynotes.service.UserService;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;
    private MailService mailService;
    private MessageSource messageSource;


    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, MailService mailService, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.mailService = mailService;
        this.messageSource = messageSource;
    }

    @Override
    public UserDto findUserById(Long id) {

        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isPresent()){
            UserDto user = userConverter.convert(optUser.get());
            return user;
        }

        throw new RuntimeException("metoda findUserById() w klasie UserServiceImpl dostała nieprawidlowe id.");
    }

    @Override
    public UserDto findUserByLogin(String login) {

        User user = userRepository.findOneByLoginIgnoreCase(login);
        return userConverter.convert(user);
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userRepository.findOneByLoginAndPassword(login, password);
        if(user == null) {
            return false;
        }
        //if user is not activated, show him message that he needs to confirm his mail
        else if(user.getStatus() != UserStatus.ACTIVATED) {
            System.out.println(user.getStatus());
            return false;
        }

        else {
            System.out.println(user.getStatus());
            return true;
        }
    }

    @Override
    public User registerNewUser(NewUserDto newUserDto) {

        //throwing exception with all not valid data
        //or creating custom validator
        if(userRepository.findOneByLoginIgnoreCase(newUserDto.getLogin()) != null){
            throw new LoginTakenException();
        }
        if(userRepository.findOneByMailIgnoreCase(newUserDto.getMail()) != null){
            throw new MailTakenException();
        }

        User newUser = new User();

        //converter
        newUser.setStatus(UserStatus.NEW);
        newUser.setLogin(newUserDto.getLogin());
        newUser.setPassword(DigestUtils.md5DigestAsHex(newUserDto.getPassword().getBytes()));//uzyc konwerter, ale tutaj podmienc plain password na szyfrowane
        newUser.setMail(newUserDto.getMail());
        newUser.setToken(UUID.randomUUID().toString());
        //setting time here
        newUser.setCreationTime(LocalDateTime.now());


        String activationTitle = messageSource.getMessage("mail.activation.title", new Object[]{
                newUser.getLogin()}, Locale.getDefault());

        String mailText = messageSource.getMessage("mail.activation.text", new Object[]{
                newUser.getLogin(), newUser.getToken()}, Locale.getDefault());

        mailService.sendConfirmationMail(newUser.getMail(), activationTitle, mailText);

        return userRepository.save(newUser);
    }

    @Override
    public boolean activateUser(String login, String token){
        User user = userRepository.findOneByLoginAndToken(login, token);

        if(user != null && UserStatus.NEW.equals(user.getStatus())){
            user.setStatus(UserStatus.ACTIVATED);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
