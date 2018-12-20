package pl.olpinski.stickynotes.service;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import pl.olpinski.stickynotes.converter.UserConverter;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.dto.NewUserDto;
import pl.olpinski.stickynotes.dto.UserDto;
import pl.olpinski.stickynotes.exception.LoginTakenException;
import pl.olpinski.stickynotes.exception.MailTakenException;
import pl.olpinski.stickynotes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserConverter userConverter;

    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
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

    //@Override
    public UserDto findUserByLogin(String login) {

        //konwersja
        User user = userRepository.findOneByLoginIgnoreCase(login);
        UserDto userDto = userConverter.convert(user);
        return userDto;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userRepository.findOneByLoginAndPassword(login, password);
        if(user == null) return false;
        else return true;
    }

    @Override
    public User registerNewUser(NewUserDto newUserDto) {


        if(userRepository.findOneByLoginIgnoreCase(newUserDto.getLogin()) != null){
            throw new LoginTakenException();
        }
        if(userRepository.findOneByMailIgnoreCase(newUserDto.getMail()) != null){
            throw new MailTakenException();
        }

        User newUser = new User();

        newUser.setLogin(newUserDto.getLogin());
        //uzyc konwerter, ale tutaj podmienc plain password na szyfrowane
        newUser.setPassword(DigestUtils.md5DigestAsHex(newUserDto.getPassword().getBytes()));
        newUser.setMail(newUserDto.getMail());

        User savedUser = userRepository.save(newUser);

        return savedUser;
    }
}
