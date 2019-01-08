package pl.olpinski.stickynotes.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.olpinski.stickynotes.data.entity.Note;
import pl.olpinski.stickynotes.data.dto.NoteCreationDto;
import pl.olpinski.stickynotes.data.dto.NoteDto;
import pl.olpinski.stickynotes.service.NoteService;
import pl.olpinski.stickynotes.service.UserService;

@Controller
@RequestMapping("/note")
public class NoteController {

    private NoteService noteService;
    private UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }



    @GetMapping("/{id}")
    public String note(@PathVariable Long id, Model model){
        NoteDto noteDto = noteService.getNoteById(id);
        model.addAttribute("note", noteDto);
        return "note/note";
    }

    @GetMapping("/{id}/edit")
    public String editNoteForm(@PathVariable Long id, Model model){

        NoteDto note = noteService.getNoteById(id);
        model.addAttribute("note", note);
        return "/note/edit_note";
    }

    @PostMapping("/{id}/edit")
    public ModelAndView editNote(NoteDto noteDto){
        Note savedNote = noteService.editNote(noteDto);

        return new ModelAndView("redirect:/note/" + savedNote.getId());
    }

    @GetMapping("/new")
    public String newNoteForm(Model model, Authentication authentication){

        model.addAttribute("user_id", authentication.getPrincipal());
        return "/note/new_note";
    }

    @PostMapping("/new")
    public ModelAndView addNewNote(Model model, NoteCreationDto noteCreationDto, Authentication authentication){

        Long userId = (Long) authentication.getPrincipal();
        noteCreationDto.setUserId(userId);
        Note savedNote = noteService.createNote(noteCreationDto);

        return new ModelAndView("redirect:/note/" + savedNote.getId());
    }

}
