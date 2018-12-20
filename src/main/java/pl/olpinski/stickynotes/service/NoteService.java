package pl.olpinski.stickynotes.service;

import pl.olpinski.stickynotes.dto.NoteDto;

public interface NoteService {

    NoteDto getNoteById(Long id);
}
