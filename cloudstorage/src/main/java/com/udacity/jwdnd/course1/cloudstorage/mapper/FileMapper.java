package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
    List<File> getUserFiles(Integer userid);

    @Select("SELECT * FROM FILES")
    List<File> getFiles();

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFile(int fileId);

    @Insert("INSERT INTO FILES (filename,contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{filesize}, #{userId}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid= #{fileId}")
    int deleteFile(Integer fileId);

    @Select("SELECT filename FROM FILES WHERE userid = #{userId} and filename = #{filename}")
    String duplicateFiles(Integer userId, String filename);


}
//    CREATE TABLE IF NOT EXISTS FILES (
//        fileId INT PRIMARY KEY auto_increment,
//        filename VARCHAR,
//        contenttype VARCHAR,
//        filesize VARCHAR,
//        userid INT,
//        filedata BLOB,
//        foreign key (userid) references USERS(userid)
//        );

//    private Integer fileId;
//    private String filename;
//    private String contentType;
//    private String filesize;
//    private Integer userId;
//    private Blob filedata;