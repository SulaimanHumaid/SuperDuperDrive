package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;
    private final HashService hashService;


    public NoteService(NoteMapper noteMapper, HashService hashService) {
        this.noteMapper = noteMapper;
        this.hashService = hashService;
    }

    public boolean isNoteAvailable(Integer id) {
        return noteMapper.getNote(id) == null;
    }

    public int createNote(Note note) {
        System.out.println("note created");
        return noteMapper.insert(new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }

    public int updateNote(Note note) {
        System.out.println("note updated");
        return noteMapper.updateNote(new Note(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription(), note.getUserId()));
    }

    public Note getNote(Integer id) {
        return noteMapper.getNote(id);
    }

    public List<Note> getAllNotes(Integer userId) {
        return noteMapper.getAllNotes(userId);
    }

    public Note deleteNote(Integer id) {
        return noteMapper.deleteNote(id);
    }

}