package pl.olpinski.stickynotes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.service.NoteService;

@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/user/{user_id}/note/{id}")
    public String note(Model model, @PathVariable Long user_id, @PathVariable Long id){
        Note note = noteService.getNoteById(id);
        if(!user_id.equals(note.getUser().getId())){
            throw new RuntimeException("unauthorized access.");
        }
        model.addAttribute("note", note);
        return "note";
    }

}
