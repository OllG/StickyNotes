package pl.olpinski.stickynotes.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.data.converter.UserConverter;
import pl.olpinski.stickynotes.data.dto.PasswordResetDto;
import pl.olpinski.stickynotes.data.dto.UserDetailsDto;
import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.data.entity.UserStatus;
import pl.olpinski.stickynotes.data.dto.UserCreationDto;
import pl.olpinski.stickynotes.data.dto.UserDto;
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
    public String getTokenByLogin(String login) {
        User user = userRepository.findOneByLoginIgnoreCase(login);
        return user.getToken();
    }

    @Override
    public void confirmNewMail(String login) {
        User user = userRepository.findOneByLoginIgnoreCase(login);
        user.setMail(user.getTempMail());
        user.setTempMail(null);
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
    }

    @Override
    public UserDto findUserByLogin(String login) {

        User user = userRepository.findOneByLoginIgnoreCase(login);
        return userConverter.convert(user);
    }
    @Override
    public User findUserByMail(String mail){
        User user = userRepository.findOneByMailIgnoreCase(mail);
        return user;
    }

    @Override
    public UserDetailsDto getDetails(Long id){
        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isPresent()){
            UserDetailsDto user = userConverter.getDetails(optUser.get());
            return user;
        }

        throw new RuntimeException("Method getDetails() recieved bad argument id");
    }

    @Override
    public boolean authenticate(String login, String password) {

        User user = userRepository.findOneByLoginIgnoreCase(login);

        if(user == null){
            return false;
        }

        else if (passwordEncoder.matches(password, user.getPassword())){
            System.out.println(user.getStatus());
            return true;
        }
        else return false;
    }

    @Override
    public User registerNewUser(UserCreationDto userCreationDto) {

        User user = userConverter.convertNewUser(userCreationDto);

        user.setPassword(passwordEncoder.encode(userCreationDto.getPassword()));
        user.setStatus(UserStatus.NEW);
        user.setToken(UUID.randomUUID().toString());
        user.setCreationTime(LocalDateTime.now());

        sendConfirmationMail(user);

        return userRepository.save(user);
    }

    @Override
    public boolean activateUser(String login, String token){
        User user = userRepository.findOneByLoginAndToken(login, token);

        if(user != null && UserStatus.NEW.equals(user.getStatus())){
            user.setStatus(UserStatus.ACTIVATED);
            user.setToken(UUID.randomUUID().toString());
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public void resendActivationMail(String mail){
        User user = userRepository.findOneByMailIgnoreCase(mail);
        if(user == null){
            throw new RuntimeException("There is no account attached to this mail");
        } else if(user.getStatus() != UserStatus.NEW){
            throw new RuntimeException("User status is not 'NEW'");
        }
        sendConfirmationMail(user);
    }

    private void sendConfirmationMail(User user){
        String activationTitle = messageSource.getMessage("mail.activation.title", new Object[]{
                user.getFirstName()}, Locale.getDefault());

        String mailText = messageSource.getMessage("mail.activation.text", new Object[]{
                user.getLogin(), user.getToken()}, Locale.getDefault());

        mailService.sendConfirmationMail(user.getMail(), activationTitle, mailText);
    }

    @Override
    public void resetPasswordAttempt(String mail){
        User user = userRepository.findOneByMailIgnoreCase(mail);
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        sendPasswordResetConfirmation(user);
    }

    @Override
    public PasswordResetDto resetPasswordDto(String login) {
        User user = userRepository.findOneByLoginIgnoreCase(login);
        PasswordResetDto passwordResetDto = new PasswordResetDto();
        passwordResetDto.setLogin(user.getLogin());
        passwordResetDto.setMail(user.getMail());
        passwordResetDto.setToken(user.getToken());
        return passwordResetDto;
    }

    private void sendPasswordResetConfirmation(User user){
        String title = "Resetting password for account " + user.getLogin();
        String text = "http://localhost:8443/new-password?login=" + user.getLogin() + "&token=" + user.getToken();
        mailService.sendConfirmationMail(user.getMail(), title, text);
    }

    @Override
    public boolean changeUserPassword(String login, String token, String password) {
        User user = userRepository.findOneByLoginAndToken(login, token);
        if(user == null){
            return false;
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        return true;
    }

    @Override
    public void setNewTempMail(Long id, String mail) {
        Optional<User> optUser = userRepository.findById(id);
        if(optUser.isPresent()){
            User user = optUser.get();
            user.setTempMail(mail);
            user.setToken(UUID.randomUUID().toString());
            User savedUser = userRepository.save(user);
            sendNewMailConfirmation(savedUser);
        }
    }

    private void sendNewMailConfirmation(User user){
        String title = "Setting ne mail for account: " + user.getLogin();
        String text = "http://localhost:8443/confirm-mail?login=" + user.getLogin() + "&token=" + user.getToken();
        mailService.sendConfirmationMail(user.getTempMail(), title, text);
    }

    @Override
    public UserStatus checkStatus(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if(!userOpt.isPresent()){
            return null;
        }
        return userOpt.get().getStatus();
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
