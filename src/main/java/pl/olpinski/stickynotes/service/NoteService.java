package pl.olpinski.stickynotes.service;

import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.dto.NewNoteDto;
import pl.olpinski.stickynotes.dto.NoteDto;

public interface NoteService {

    NoteDto getNoteById(Long id);

    Note createNote(NewNoteDto newNoteDto);

    Note editNote(NoteDto noteDto);
}
