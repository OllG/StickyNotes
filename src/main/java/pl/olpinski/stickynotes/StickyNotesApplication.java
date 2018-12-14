package pl.olpinski.stickynotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.olpinski.stickynotes.repository.NoteRepository;

@SpringBootApplication
public class StickyNotesApplication {

    //jdbc:h2:mem:testdb
    public static void main(String[] args) {
        SpringApplication.run(StickyNotesApplication.class, args);
    }

}

