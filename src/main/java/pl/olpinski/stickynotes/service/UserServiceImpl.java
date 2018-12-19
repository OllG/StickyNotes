package pl.olpinski.stickynotes.service;

import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserById(Long id) {

        Optional<User> optUser = userRepository.findById(id);

        if(optUser.isPresent()){
            return optUser.get();
        }

        throw new RuntimeException("metoda findUserById() w klasie UserServiceImpl dostała nieprawidlowe id.");
    }

    //@Override
    public User findUserByLogin(String login) {

        //konwersja
        User user = userRepository.findOneByLogin(login);
        return user;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = userRepository.findOneByLoginAndPassword(login, password);
        if(user == null) return false;
        else return true;
    }


}
