package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadService {

    private FileMapper fileMapper;

    public FileUploadService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int addFile(File file){
        return fileMapper.insertFile(file);
    }

    public int deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }

    public List<File> getUserFiles(Integer userId){
        return fileMapper.getUserFiles(userId);
    }

    public File getFile(Integer fileId){
        return fileMapper.getFile(fileId);
    }

    public boolean fileDuplicate(Integer userId, String filename){
        return fileMapper.duplicateFiles(userId,filename) != null; //it's true, if it's null - theres no duplicates
    }
}


//    @Select("SELECT * FROM FILES WHERE userid=#{userId}")
//    List<File> getUserFiles(Integer userid);
//
//    @Select("SELECT * FROM FILES")
//    List<File> getFiles();
//
//    @Select("SELECT * FROM FILES WHERE fileId = #{fileId")
//    File getFile(int fileId);
//
//    @Insert("INSERT INTO FILES (filename,contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contentType}, #{filesize}, #{userId}, #{filedata})")
//    @Options(useGeneratedKeys = true, keyProperty = "fileId")
//    int insertFile(File file);
//
//    @Delete("DELETE FROM FILES WHERE fileid= #{fileId}")
//    int deleteFile(Integer fileId);
//
//    @Select("SELECT filename FROM FILES WHERE userid = #{userId} and filename = #{filename}")
//    String DuplicateFiles(Integer userId, String filename);