package pl.olpinski.stickynotes.service;

import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.dto.NoteCreationDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;
import pl.olpinski.stickynotes.data.entity.User;

public interface NoteService {

    NoteDto getNoteById(Long id);

    NoteDto getNoteByPerUserIdAndUser(Long id, User user);

    Note createNote(NoteCreationDto noteCreationDto);

    Note editNote(NoteDto noteDto);
}
