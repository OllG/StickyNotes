package pl.olpinski.stickynotes.data.converter;

import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.data.dto.NewUserDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;
import pl.olpinski.stickynotes.data.dto.UserDto;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private NoteConverter noteConverter;

    public UserConverter(NoteConverter noteConverter) {
        this.noteConverter = noteConverter;
    }

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

    public User convertNewUser(NewUserDto newUserDto){

        User user = new User();

        user.setLogin(newUserDto.getLogin());
        user.setPassword(newUserDto.getPassword());
        user.setMail(newUserDto.getMail());
        user.setFirstName(newUserDto.getFirstName());
        user.setLastName(newUserDto.getLastName());

        return user;
    }
}
