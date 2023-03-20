package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialsId}")
    Credentials getCredentials(Integer credentialsId);

    @Insert("INSERT INTO CREDENTIALS (credentialid, url, username, key, password, userid) VALUES(#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getAllCredentials(Integer userId);

    @Select("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials deleteCredentials(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET  url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId} WHERE credentialid = #{credentialId}")
    int updateCredentials(Credentials credentials);
}
