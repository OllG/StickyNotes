package pl.olpinski.stickynotes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.olpinski.stickynotes.domain.Note;

@Repository
public interface NoteRepository extends CrudRepository <Note, Long> {
}
