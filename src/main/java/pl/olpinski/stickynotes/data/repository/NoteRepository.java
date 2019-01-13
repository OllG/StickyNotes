package pl.olpinski.stickynotes.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.entity.User;

@Repository
public interface NoteRepository extends CrudRepository <Note, Long> {

    Note findByPerUserIdAndUser(Long perUserId, User user);
}
