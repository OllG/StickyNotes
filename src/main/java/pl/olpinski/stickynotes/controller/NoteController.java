package pl.olpinski.stickynotes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.service.NoteService;

@Controller
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/user/new_note")
    public String newNoteForm(Model model, Authentication authentication){

        model.addAttribute("user_id", authentication.getPrincipal());
        return "new_note";
    }

    @PostMapping("/user/new_note")
    public ModelAndView addNewNote(Model model, @RequestParam("title") String title, @RequestParam("content") String content, Authentication authentication){

        Long userId = (Long) authentication.getPrincipal();


        return new ModelAndView("redirect:/user/");

    }
}
