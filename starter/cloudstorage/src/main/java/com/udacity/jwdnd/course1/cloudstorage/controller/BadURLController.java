package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class BadURLController implements ErrorController {

    @GetMapping
    public String getError(){
        return "BadURL";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
