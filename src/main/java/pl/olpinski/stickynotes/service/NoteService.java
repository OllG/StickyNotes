package pl.olpinski.stickynotes.service;

import pl.olpinski.stickynotes.domain.Note;

public interface NoteService {

    Note getNoteById(Long id);
}
