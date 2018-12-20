package pl.olpinski.stickynotes.service;

import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.converter.UserConverter;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.dto.UserDto;
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
        User user = userRepository.findOneByLogin(login);
        UserDto userDto = userConverter.convert(user);
        return userDto;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userRepository.findOneByLoginAndPassword(login, password);
        if(user == null) return false;
        else return true;
    }
}
