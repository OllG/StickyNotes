package pl.olpinski.stickynotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StickyNotesApplication {

    //jdbc:h2:mem:testdb
    public static void main(String[] args) {
        SpringApplication.run(StickyNotesApplication.class, args);
    }

}

