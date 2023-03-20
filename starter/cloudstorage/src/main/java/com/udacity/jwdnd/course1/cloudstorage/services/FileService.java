package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final HashService hashService;
    private final UserMapper userMapper;


    public FileService(FileMapper fileMapper, HashService hashService, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public boolean isFileAvailable(Integer id) {
        return fileMapper.getFile(id) == null;
    }

    public int createFile(File file) {
        System.out.println("file created");
//        List<File> files = fileMapper.getAllFiles(userMapper.getUser(name).getUserId());
        return fileMapper.insert(new File(null, file.getFilename(), file.getContentType(), file.getFileSize(), file.getUserid(), file.getFileData()));
    }

    public File getFile(Integer id) {
        return fileMapper.getFile(id);
    }

    public List<File> getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public File deleteFile(Integer id) {
        return fileMapper.deleteFile(id);
    }

}