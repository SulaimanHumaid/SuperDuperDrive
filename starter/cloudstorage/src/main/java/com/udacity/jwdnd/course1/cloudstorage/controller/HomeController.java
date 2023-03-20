package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;
    private CredentialsService credentialsService;
    private FileService fileService;
    private UserMapper userMapper;

    public HomeController(NoteService noteService, CredentialsService credentialsService, FileService fileService, UserMapper userMapper) {
        this.noteService = noteService;
        this.credentialsService = credentialsService;
        this.fileService = fileService;
        this.userMapper = userMapper;
    }

    @GetMapping()
    public String loginView(@ModelAttribute Note note, @ModelAttribute Credentials credentials, @ModelAttribute File file, Model model, Authentication authentication) {
        String name = authentication.getName();

        model.addAttribute("notes", this.noteService.getAllNotes(userMapper.getUser(name).getUserId()));
        model.addAttribute("credentials", this.credentialsService.getAllCredentials(userMapper.getUser(name).getUserId()));
        model.addAttribute("files", this.fileService.getAllFiles(userMapper.getUser(name).getUserId()));

        return "home";
    }
}