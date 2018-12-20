package pl.olpinski.stickynotes.service;

import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.dto.NoteDto;

public interface NoteService {

    NoteDto getNoteById(Long id);
    Note saveNote(NoteDto noteDto);
}
