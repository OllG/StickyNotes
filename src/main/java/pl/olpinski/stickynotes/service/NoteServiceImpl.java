package pl.olpinski.stickynotes.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.converter.NoteConverter;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.dto.NoteDto;
import pl.olpinski.stickynotes.repository.NoteRepository;

import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private NoteRepository noteRepository;
    private NoteConverter noteConverter;

    public NoteServiceImpl(NoteRepository noteRepository, NoteConverter noteConverter) {
        this.noteRepository = noteRepository;
        this.noteConverter = noteConverter;
    }

    @Override
    public NoteDto getNoteById(Long id){
        Optional<Note> optNote = noteRepository.findById(id);
        if (optNote.isPresent()){
            NoteDto note = noteConverter.convert(optNote.get());
            return note;
        }
        throw new RuntimeException("podano nieistniejace id do metody getNoteById()");
    }

    @Override
    public Note saveNote(NoteDto noteDto) {
        Note note = noteConverter.deconvert(noteDto);
        Note savedNote = noteRepository.save(note);
        return savedNote;
    }
}
