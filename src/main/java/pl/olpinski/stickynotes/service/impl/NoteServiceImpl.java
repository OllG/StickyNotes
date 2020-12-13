package pl.olpinski.stickynotes.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.olpinski.stickynotes.data.converter.NoteConverter;
import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.dto.NoteCreationDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;
import pl.olpinski.stickynotes.data.repository.NoteRepository;
import pl.olpinski.stickynotes.data.repository.UserRepository;
import pl.olpinski.stickynotes.service.NoteService;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private NoteRepository noteRepository;
    private NoteConverter noteConverter;
    private UserRepository userRepository;

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
    public boolean HasAccess(Long noteId, String userLogin)
    {
        String loginByNote = noteRepository.findById(noteId).get().getUser().getLogin();
        return loginByNote.equalsIgnoreCase(userLogin);
    }

    @Override
    public Note createNote(NoteCreationDto noteDto) {
        Note note = noteConverter.createNewNoteConversion(noteDto);
        note.setCreationTime(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @Override
    public Note editNote(NoteDto noteDto) {
        Optional<Note> optNote = noteRepository.findById(noteDto.getId());
        if (!optNote.isPresent()){
            throw new RuntimeException("Próbujesz edytować notatkę, która nie istnieje");
        }
        Note editedNote = noteConverter.editNoteConversion(noteDto);
        editedNote.setCreationTime(optNote.get().getCreationTime());
        editedNote.setLastEditionTime(LocalDateTime.now());
        Note savedNote = noteRepository.save(editedNote);
        return savedNote;
    }

    @Override
    public void RemoveNote(Long id) {
        noteRepository.deleteById(id);
    }
}
