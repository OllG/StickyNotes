package pl.olpinski.stickynotes.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.domain.Note;
import pl.olpinski.stickynotes.dto.NoteDto;
import pl.olpinski.stickynotes.service.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public String note(@PathVariable Long id, Model model){
        NoteDto noteDto = noteService.getNoteById(id);
        model.addAttribute("note", noteDto);
        return "note";
    }

    @GetMapping("/{id}/edit")
    public String editNoteForm(@PathVariable Long id, Model model){

        NoteDto note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "edit_note";
    }

    @GetMapping("/new")
    public String newNoteForm(Model model, Authentication authentication){

        model.addAttribute("user_id", authentication.getPrincipal());
        return "new_note";
    }

    @PostMapping("/new")
    public ModelAndView addNewNote(Model model, @RequestParam("title") String title, @RequestParam("content") String content, Authentication authentication){

        Long userId = (Long) authentication.getPrincipal();
        NoteDto noteDto = new NoteDto();
        noteDto.setUserId(userId);
        noteDto.setTitle(title);
        noteDto.setContent(content);

        Note savedNote = noteService.saveNote(noteDto);

        return new ModelAndView("redirect:/note/" + savedNote.getId());
    }

}
