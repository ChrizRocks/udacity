package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE CREDENTIALS.userid=#{userId}")
    List<Credential> getUserCredentials(@Param("userId") Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credential findByCredetialId(@Param("credentialId") Integer credentialId);

    @Select("SELECT 'key' FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    String findKey(Integer credentialId);

    //Credential findByCredentialId(@Param("credentialId") Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES(#{url},#{username}, #{key}, #{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET "+ "url = #{url}, username = #{username}, key = #{key}, password = #{password}, userid = #{userId}" + "WHERE credentialid = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    int deleteCredential(int credentialId);
}
