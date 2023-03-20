package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES(#{noteId}, #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> getAllNotes(Integer userId);

    @Select("DELETE FROM NOTES WHERE noteid = #{noteId}")
    Note deleteNote(Integer noteId);

    @Update("UPDATE NOTES SET noteid = #{noteId} , notetitle = #{noteTitle}, noteDescription = #{noteDescription}, userid = #{userId} WHERE noteid = #{noteId}")
    int updateNote(Note note);
}