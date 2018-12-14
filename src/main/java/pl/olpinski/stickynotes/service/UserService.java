package pl.olpinski.stickynotes.service;

import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.domain.User;

public interface UserService {

    User findUserById(Long id);
}
