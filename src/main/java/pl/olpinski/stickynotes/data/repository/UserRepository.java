package pl.olpinski.stickynotes.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.olpinski.stickynotes.data.entity.User;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    User findOneByLoginAndPassword(String login, String password);
    User findOneByLoginIgnoreCase(String login);
    User findOneByMailIgnoreCase(String mail);
    User findOneByLoginAndToken(String login, String token);
}
