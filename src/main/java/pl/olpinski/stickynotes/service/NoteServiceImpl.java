package pl.olpinski.stickynotes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.repository.NoteRepository;

import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private NoteRepository noteRepository;

    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public Note getNoteById(Long id){
        Optional<Note> optNote = noteRepository.findById(id);
        if (optNote.isPresent()){
            return optNote.get();
        }
        throw new RuntimeException("podano nieistniejace id do metody getNoteById()");
    }
}
