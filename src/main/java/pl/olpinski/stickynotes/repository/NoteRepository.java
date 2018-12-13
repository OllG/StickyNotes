package pl.olpinski.stickynotes.repository;

import org.springframework.data.repository.CrudRepository;
import pl.olpinski.stickynotes.domain.Note;

public interface NoteRepository extends CrudRepository <Note, Long> {
}
