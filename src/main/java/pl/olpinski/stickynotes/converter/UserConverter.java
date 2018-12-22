package pl.olpinski.stickynotes.converter;

import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.dto.NoteDto;
import pl.olpinski.stickynotes.dto.UserDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter implements Converter <User, UserDto> {

    private NoteConverter noteConverter;

    public UserConverter(NoteConverter noteConverter) {
        this.noteConverter = noteConverter;
    }

    @Override
    public UserDto convert(User object) {
        //in progress
        UserDto userDto = new UserDto();

        userDto.setId(object.getId());
        userDto.setLogin(object.getLogin());
        userDto.setPassword(object.getPassword());
        userDto.setMail(object.getMail());

        Set<Note> set = object.getNotes();
        Set<NoteDto> noteDtoSet;// = new HashSet<>();

        noteDtoSet = set.stream().map(note -> noteConverter.convert(note)).collect(Collectors.toSet());

        userDto.setNotes(noteDtoSet);

        return userDto;
    }

    @Override
    public User deconvert(UserDto dtoObject) {
        return null;
    }
}
