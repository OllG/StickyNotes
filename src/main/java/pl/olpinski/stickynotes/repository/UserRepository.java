package pl.olpinski.stickynotes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.olpinski.stickynotes.domain.User;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
}
