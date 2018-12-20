package pl.olpinski.stickynotes.converter;

import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.dto.NoteDto;
import pl.olpinski.stickynotes.repository.UserRepository;

@Component
public class NoteConverter implements Converter<Note, NoteDto> {

    private UserRepository userRepository;

    public NoteConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        Note note = new Note();
        note.setId(dtoObject.getId());
        note.setTitle(dtoObject.getTitle());
        note.setContent(dtoObject.getContent());

        if(userRepository.findById(dtoObject.getUserId()).isPresent()) {
            note.setUser(userRepository.findById(dtoObject.getUserId()).get());
            return note;
        }

        throw new RuntimeException("deconvert method get wrond user_id");
        //return null;
    }
}
