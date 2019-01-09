package pl.olpinski.stickynotes.data.converter;

import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.data.dto.NoteCreationDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;
import pl.olpinski.stickynotes.data.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class NoteConverter{

    private UserRepository userRepository;

    public NoteConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //@Override
    public NoteDto convert(Note note) {
        //zmienic user id na user dto i ustawiac przy konwersji usera
        Long id = note.getId();
        Long userId = note.getUser().getId();
        String title = note.getTitle();
        String content = note.getContent();
        LocalDateTime creationTime = note.getCreationTime();
        LocalDateTime lastEditionTime = note.getLastEditionTime();

        NoteDto noteDto = new NoteDto();
        noteDto.setId(id);
        noteDto.setUserId(userId);
        noteDto.setTitle(title);
        noteDto.setContent(content);
        noteDto.setCreationTime(creationTime);
        noteDto.setLastEditionTime(lastEditionTime);

        return noteDto;
    }


    public Note createNewNoteConversion(NoteCreationDto noteCreationDto) {

        Note note = new Note();
        note.setTitle(noteCreationDto.getTitle());
        note.setContent(noteCreationDto.getContent());

        if(userRepository.findById(noteCreationDto.getUserId()).isPresent()) {
            note.setUser(userRepository.findById(noteCreationDto.getUserId()).get());
            return note;
        }

        throw new RuntimeException("deconvert method get wrond user_id");
        //return null;
    }

    public Note editNoteConversion(NoteDto noteDto){
        Note note = new Note();
        note.setId(noteDto.getId());

        Optional<User> user = userRepository.findById(noteDto.getUserId());
        if(!user.isPresent()){
            throw new RuntimeException("Nie można znaleźć użytkownika");
        }
        note.setUser(user.get());
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        note.setCreationTime(noteDto.getCreationTime());
        note.setLastEditionTime(noteDto.getLastEditionTime());
        return note;
    }
}
