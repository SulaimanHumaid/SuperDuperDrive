package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Utilities;
import java.io.IOException;
import java.util.List;



@Controller()
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final UserMapper userMapper;
    private final FileMapper fileMapper;

    public FileController(FileService fileService, UserMapper userMapper, FileMapper fileMapper) {
        this.fileService = fileService;
        this.userMapper = userMapper;
        this.fileMapper = fileMapper;
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Authentication authentication, Model model) {
        System.out.println("uploadFile");
        String name = authentication.getName();
        File file;
        try {
            file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(), String.valueOf(multipartFile.getSize()), userMapper.getUser(name).getUserId(), multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        file.setUserid(userMapper.getUser(name).getUserId());
        List<File> files = fileService.getAllFiles(userMapper.getUser(name).getUserId());
        boolean isFound = false;

        for (File f : files) {
            if(f.getFilename().equals(file.getFilename())) {
                isFound = true;
                break;
            }
        }

        if (String.valueOf(multipartFile.getSize()).length() <= 1 || isFound) {
            model.addAttribute("result", "failure");
        } else {
            fileService.createFile(file);
            model.addAttribute("result", "success");
        }

        model.addAttribute("files", this.fileService.getAllFiles(userMapper.getUser(name).getUserId()));
        return "result";
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(Model model, @PathVariable Integer fileId, Authentication authentication) {
        String name = authentication.getName();
        fileService.deleteFile(fileId);
        model.addAttribute("files", this.fileService.getAllFiles(userMapper.getUser(name).getUserId()));
        model.addAttribute("result", "success");
        return "result";
    }

    @GetMapping("/download/{fileid}")
    @ResponseBody
    public byte[] downloadFile(@ModelAttribute File file, @PathVariable Integer fileid, Authentication authentication, Model model) {
        System.out.println("downloadFile");

        File test = fileService.getFile(fileid);
        byte[] testData = test.getFileData();
        System.out.println(testData);
        model.addAttribute("files", this.fileService.getAllFiles(userMapper.getUser(authentication.getName()).getUserId()));
        return testData;
    }
}