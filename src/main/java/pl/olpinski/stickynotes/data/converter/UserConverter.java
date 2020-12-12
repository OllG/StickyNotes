package pl.olpinski.stickynotes.data.converter;

import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.data.dto.UserDetailsDto;
import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.entity.User;
import pl.olpinski.stickynotes.data.dto.UserCreationDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;
import pl.olpinski.stickynotes.data.dto.UserDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

        List<NoteDto> noteDtoList = new ArrayList<>(noteDtoSet);
        if(noteDtoList != null)
            noteDtoList.sort(new SortByEditDate());

        userDto.setNotes(noteDtoList);

        return userDto;
    }

    public UserDetailsDto getDetails(User user){
        UserDetailsDto userDetailsDto = new UserDetailsDto();

        userDetailsDto.setId(user.getId());
        userDetailsDto.setLogin(user.getLogin());
        userDetailsDto.setPassword(user.getPassword());
        userDetailsDto.setMail(user.getMail());
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setLastName(user.getLastName());
        userDetailsDto.setCreationTime(user.getCreationTime());

        return userDetailsDto;
    }

    public User convertNewUser(UserCreationDto userCreationDto){

        User user = new User();

        user.setLogin(userCreationDto.getLogin());
        user.setPassword(userCreationDto.getPassword());
        user.setMail(userCreationDto.getMail());
        user.setFirstName(userCreationDto.getFirstName());
        user.setLastName(userCreationDto.getLastName());

        return user;
    }
}

class SortByEditDate implements Comparator<NoteDto>
{
    public  int compare(NoteDto a, NoteDto b){

        return GetLastEditTimeOrCreationTime(b).compareTo(GetLastEditTimeOrCreationTime(a));
    }



    private LocalDateTime GetLastEditTimeOrCreationTime(NoteDto note)
    {
        if(note.getLastEditionTime() != null){
            return  note.getLastEditionTime();
        }
        else{
            return note.getCreationTime();
        }
    }
}
