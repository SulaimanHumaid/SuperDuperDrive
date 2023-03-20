package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteController(NoteService noteService, UserMapper userMapper, NoteMapper noteMapper) {
        this.noteService = noteService;
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

//    @GetMapping
//    public String getNotes(Model model) {
//        model.addAttribute("notes", this.noteService.getAllNotes());
//        return "home";
//    }


    @PostMapping("/save")
    public String saveNote(@ModelAttribute Note note, Authentication authentication, Model model) {
        System.out.println("saveNote");
        String name = authentication.getName();
//        System.out.println("userMapper.getUser(name).getUsername()");
//        System.out.println(userMapper.getUser(name).getUsername());
//        System.out.println("userMapper.getUser(name).getUserId()");
//        System.out.println(userMapper.getUser(name).getUserId());
        note.setUserId(userMapper.getUser(name).getUserId());
        if (note.getNoteId() != null) {
            noteService.updateNote(note);
        } else {
            noteService.createNote(note);
        }

        model.addAttribute("notes", this.noteService.getAllNotes(userMapper.getUser(name).getUserId()));
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/delete/{noteid}")
    public String deleteNote(Model model, @PathVariable Integer noteid, Authentication authentication) {
        String name = authentication.getName();
        noteService.deleteNote(noteid);
        model.addAttribute("notes", this.noteService.getAllNotes(userMapper.getUser(name).getUserId()));
        model.addAttribute("result", "success");
        return "result";
    }

}