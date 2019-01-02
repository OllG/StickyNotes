package pl.olpinski.stickynotes.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.olpinski.stickynotes.data.converter.UserConverter;
import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.data.entity.UserStatus;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.data.repository.UserRepository;
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
    private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter, MailService mailService, MessageSource messageSource, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.mailService = mailService;
        this.messageSource = messageSource;
        this.passwordEncoder = passwordEncoder;
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

        User user = userRepository.findOneByLoginIgnoreCase(login);
        boolean passwordMatch = passwordEncoder.matches(password, user.getPassword());

        if(user == null) {
            System.out.println("Nie ma takiego loginu i hasła");
            return false;
        }
        //if user is not activated, show him message that he needs to confirm his mail
        else if(user.getStatus() != UserStatus.ACTIVATED) {
            System.out.println(user.getStatus());
            return false;
        }

        else if (passwordMatch){
            System.out.println(user.getStatus());
            return true;
        }
        else return false;
    }

    @Override
    public User registerNewUser(NewUserDto newUserDto) {

        System.out.println(passwordEncoder);

        User user = userConverter.convertNewUser(newUserDto);

        user.setPassword(passwordEncoder.encode(newUserDto.getPassword()));
        user.setStatus(UserStatus.NEW);
        user.setToken(UUID.randomUUID().toString());
        user.setCreationTime(LocalDateTime.now());

        sendConfirmationMail(user);

        return userRepository.save(user);
    }

    private void sendConfirmationMail(User user){
        String activationTitle = messageSource.getMessage("mail.activation.title", new Object[]{
                user.getFirstName()}, Locale.getDefault());

        String mailText = messageSource.getMessage("mail.activation.text", new Object[]{
                user.getLogin(), user.getToken()}, Locale.getDefault());

        mailService.sendConfirmationMail(user.getMail(), activationTitle, mailText);
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

    @Override
    public boolean isMailRegistered(String mail){
        return userRepository.findOneByMailIgnoreCase(mail) != null;
    }

    @Override
    public boolean isLoginTaken(String login) {
        return userRepository.findOneByLoginIgnoreCase(login) != null;
    }
}
