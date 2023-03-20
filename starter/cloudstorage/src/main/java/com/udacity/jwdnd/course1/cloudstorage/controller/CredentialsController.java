package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/credentials")
public class CredentialsController {

    private final CredentialsService credentialsService;
    private final UserMapper userMapper;
    private final CredentialsMapper credentialsMapper;

    public CredentialsController(CredentialsService credentialsService, UserMapper userMapper, CredentialsMapper credentialsMapper) {
        this.credentialsService = credentialsService;
        this.userMapper = userMapper;
        this.credentialsMapper = credentialsMapper;
    }

//    @GetMapping
//    public String getCredentials(Model model) {
//        model.addAttribute("credentials", this.credentialsService.getAllCredentials());
//        return "home";
//    }


    @PostMapping("/save")
    public String saveCredentials(@ModelAttribute Credentials credentials, Authentication authentication, Model model) {
        System.out.println("saveCredentials");
        String name = authentication.getName();
        credentials.setUserId(userMapper.getUser(name).getUserId());

        if (credentials.getCredentialId() != null) {
            credentialsService.updateCredentials(credentials);
        } else {
            credentialsService.createCredentials(credentials);
        }

        model.addAttribute("credentials", this.credentialsService.getAllCredentials(userMapper.getUser(name).getUserId()));
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/delete/{credentialsid}")
    public String deleteCredentials(Model model, @PathVariable Integer credentialsid, Authentication authentication) {
        String name = authentication.getName();
        credentialsService.deleteCredentials(credentialsid);
        model.addAttribute("credentials", this.credentialsService.getAllCredentials(userMapper.getUser(name).getUserId()));
        model.addAttribute("result", "success");
        return "result";
    }

}