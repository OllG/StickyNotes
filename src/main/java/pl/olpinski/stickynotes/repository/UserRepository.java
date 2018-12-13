package pl.olpinski.stickynotes.repository;

import org.springframework.data.repository.CrudRepository;
import pl.olpinski.stickynotes.domain.User;

public interface UserRepository extends CrudRepository <User, Long> {
}
