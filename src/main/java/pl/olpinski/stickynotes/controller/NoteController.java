package pl.olpinski.stickynotes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.domain.User;
import pl.olpinski.stickynotes.repository.UserRepository;
import pl.olpinski.stickynotes.service.NoteService;

import java.util.Optional;

@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note")
    public String note(Model model){
        Note note = noteService.getNoteById(1L);
        model.addAttribute("note", note);
        return "note";
    }
}
