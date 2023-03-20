package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFile(Integer fileId);

    @Insert("INSERT INTO FILES (fileid, filename, contenttype, filesize, userid, filedata) VALUES(#{fileId}, #{filename}, #{contentType}, #{fileSize}, #{userid}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllFiles(Integer userId);

    @Select("DELETE FROM FILES WHERE fileid = #{fileId}")
    File deleteFile(Integer fileId);

//    @Update("UPDATE FILES SET  filename = #{filename}, contenttype = #{contentType}, filesize = #{fileSize}, userid = #{userid} , filedata = #{fileData} WHERE fileid = #{fileId}")
//    int updateFile(File file);
}
