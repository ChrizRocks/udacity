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
