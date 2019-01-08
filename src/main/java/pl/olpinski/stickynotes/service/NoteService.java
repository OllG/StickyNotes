package pl.olpinski.stickynotes.service;

import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.dto.NewNoteDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;

public interface NoteService {

    NoteDto getNoteById(Long id);

    Note createNote(NewNoteDto newNoteDto);

    Note editNote(NoteDto noteDto);
}
