package pl.olpinski.stickynotes.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.repository.NoteRepository;
import pl.olpinski.stickynotes.repository.UserRepository;

@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {

    private NoteRepository noteRepository;
    private UserRepository userRepository;

    public DataLoad(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        init();
    }

    public void init(){

        Note firstNote = new Note();
        firstNote.setContent("fadbfakfasfnbjangandglnadslkfgagkgadg");
        firstNote.setTitle("Test");

        User firstUser = new User();
        firstUser.setLogin("login");
        firstUser.setMail("asdsa@das");
        firstUser.setPassword("qwerty");

        firstNote.setUser(firstUser);
        firstUser.getNotes().add(firstNote);

        userRepository.save(firstUser);
    }
}
