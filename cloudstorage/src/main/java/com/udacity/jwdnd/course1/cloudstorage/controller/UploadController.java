package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

@Controller
public class UploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String fileUploadHandler(@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication, Model model) throws IOException {
        String fileUploadError = "New file added successfully!";
        if(fileUpload.isEmpty()){
            fileUploadError = "File is empty. Please try again.";
            model.addAttribute("Success",false);
            model.addAttribute("message",fileUploadError);
            return "result";
        }
        int userId= userService.getUser(authentication.getName()).getUserId();
        if(fileUploadService.fileDuplicate(userId,fileUpload.getOriginalFilename())){
            fileUploadError = "File already exists. Upload failed!";
            model.addAttribute("Success",false);
            model.addAttribute("message",fileUploadError);
            return "result";
        } else {
            String filename = fileUpload.getOriginalFilename();
            String contentType = fileUpload.getContentType();
            String filesize = String.valueOf(fileUpload.getSize());
            byte[] filedata = fileUpload.getBytes();
            File file = new File(filename,contentType,filesize,userId,filedata);
            fileUploadService.addFile(file);
            model.addAttribute("Success",true);
            model.addAttribute("message", fileUploadError);
            return "result";
        }
//
//        Integer user = userService.getUser(authentication.getName()).getUserId();
//        byte[] bytes = ByteBuffer.allocate(8).putLong(fileUpload.getSize()).array();
//
//        String filesize = new String(bytes);
//        System.out.println(filesize);
//        byte[] fileData = fileUpload.getBytes();
//
//        if(fileData.length == 0){ //file is empty
//            fileUploadError = "File is empty. Pleas try again.";
//            return "result";
//        }
//
//        if(fileUploadService.fileDuplicate(user, fileUpload.getName())) {//file already existing from that user
//            fileUploadError = "File with that name already exists!";
//            return "result";
//        }
//        //public File(String filename, String contentType, String filesize, Integer userId, byte[] filedata)
//        File neuesFile = new File(fileUpload.getOriginalFilename(),fileUpload.getContentType(),filesize,user,fileData);
//        int fileAdded = fileUploadService.addFile(neuesFile);
//        if(fileAdded<1){
//            fileUploadError = "Fileupload was not successful, please try again!";
//        }
//        if(fileUploadError==null){ //fileUpload successful
//            model.addAttribute("fileUploadSuccess", true);
//        } else {
//            model.addAttribute("fileUploadError", fileUploadError);
//        }
//
//
//        //  List<File> files = fileUploadService.getUserFiles(user);
//        //model.addAttribute("files", files);
//        return "result";
    }

}
