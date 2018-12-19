package pl.olpinski.stickynotes.converter;

import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.dto.NoteDto;


public class NoteConverter implements Converter<Note, NoteDto> {

    @Override
    public NoteDto convert(Note object) {
        //zmienic user id na user dto i ustawiac przy konwersji usera
        Long id = object.getId();
        Long userId = object.getUser().getId();
        String title = object.getTitle();
        String content = object.getContent();

        NoteDto noteDto = new NoteDto();
        noteDto.setId(id);
        noteDto.setUserId(userId);
        noteDto.setTitle(title);
        noteDto.setContent(content);

        return noteDto;
    }

    @Override
    public Note deconvert(NoteDto dtoObject) {
        return null;
    }
}
