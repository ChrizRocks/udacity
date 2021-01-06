package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CreateNotesService createNotesService;
    @Autowired
    private CredentialService credentialService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private HashService hashService;


    @RequestMapping("/home")
    public String getHomePage(Authentication auth, Model model){
        Integer user = userService.getUser(auth.getName()).getUserId();
        List<File> files = fileUploadService.getUserFiles(user);
        List<Note> notes = createNotesService.getNotes(user);
        List<Credential> credentials = credentialService.getAllCredentials(user);
        model.addAttribute("files",files);
        model.addAttribute("notes",notes);
        model.addAttribute("credentials", credentials);
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("activeTab","files");
        return "home";
    }

    @RequestMapping("/result")
    public String result(){
        return "result";
    }


    @GetMapping("/file/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId, Model model){
        int res = fileUploadService.deleteFile(fileId);
        model.addAttribute("activeTab","files");
        if(res==1){
            model.addAttribute("Success",true);
            model.addAttribute("message","File was successfully deleted!");
        } else {
            model.addAttribute("Success",false);
            model.addAttribute("message","Error while deleting file, File not deleted!");
        }
        return "result";
    }

    @GetMapping("/file/view/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer fileId, Authentication authentication, Model model)  {
        File file = fileUploadService.getFile(fileId);
        model.addAttribute("activeTab","files");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType())).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + file.getFilename() + "\"").body(new
                        ByteArrayResource(file.getFiledata()));
    }

    @PostMapping("/createNotes")
    public String notesController(@ModelAttribute("userNotesObject") Note userNotesObject, Authentication auth, Model model){
        model.addAttribute("activeTab","notes");
        if(userNotesObject.getNoteId() != null) {
            int res= createNotesService.updateNote(userNotesObject);
            if(res==1) {
                model.addAttribute("Success", true);
                model.addAttribute("message","Note was successfully updated.");
            } else {
                model.addAttribute("Success", false);
                model.addAttribute("message","Note was not updated!");
            }
            return "result";
        }
        Integer UID = userService.getUser(auth.getName()).getUserId() ;
        userNotesObject.setUserId(UID);
        int res = createNotesService.createNote(userNotesObject);
        if(res==1) {
            model.addAttribute("Success", true);
            model.addAttribute("message","Note was successfully created.");
        } else {
            model.addAttribute("Success", false);
            model.addAttribute("message","Note was not created!");
        }
        return "result";
    }

    @GetMapping("/note/delete/{noteId}")
    public String deleteNote(@PathVariable String noteId, Model model) {
        Integer x = Integer.parseInt(noteId);
        int res= createNotesService.deleteNote(x);
        model.addAttribute("activeTab","notes");
        if(res==1){
            model.addAttribute("Success",true);
            model.addAttribute("message","Note was successfully deleted");
        } else {
            model.addAttribute("Success",false);
            model.addAttribute("message","Note was not deleted!");
        }
        return "result";
    }
    //("INSERT INTO CREDENTIALS(url, username, key, password, userid) VALUES(#{url},#{username}, #{key}, #{password},#{userId})"
    @PostMapping("/addCredentials")
    public String credentialController(@ModelAttribute("credentials") Credential credentials, Authentication auth, Model model){
        model.addAttribute("activeTab","credentials");
        if(credentials.getCredentialId() != null) {
            int res=credentialService.updateCredential(credentials);
            if (res ==1){
                model.addAttribute("Success",true);
                model.addAttribute("message","Credential was successfully updated.");
            } else {
                model.addAttribute("Success", false);
                model.addAttribute("message","Credential was not updated!");
            }
            return "result";
        }
        Integer UID = userService.getUser(auth.getName()).getUserId() ;
        credentials.setUserId(UID);
        int res = credentialService.createCredential(credentials);
        if (res ==1){
            model.addAttribute("Success",true);
            model.addAttribute("message","Credential was successfully added.");
        } else {
            model.addAttribute("Success", false);
            model.addAttribute("message","Credential was not added!");
        }
        return "result";
    }

    @GetMapping("/credential/delete/{credentialId}")
    public String deleteCredential(@PathVariable String credentialId, Model model) {
        model.addAttribute("activeTab","credentials");
        int res = credentialService.deleteCredential(Integer.parseInt(credentialId));
        if (res==1){
            model.addAttribute("Success",true);
            model.addAttribute("message","Credential was successfully deleted.");
        } else  {
            model.addAttribute("Success",false);
            model.addAttribute("message","Credential was not deleted!");
        }
        return "result";
    }

//    @GetMapping(value = "/decode-password")
//    @ResponseBody
//    public Map<String, String> decodePassword(@RequestParam Integer credentialId){
//        Credential credential = credentialService.decodePassword(credentialId);
//        String encryptedPassword = credential.getPassword();
//        String encodedKey = credential.getKey();
//        EncryptionService encryptionService = new EncryptionService();
//        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);
//        Map<String, String> response = new HashMap<>();
//        response.put("decryptedPassword", decryptedPassword);
//        return response;
//    }


}
