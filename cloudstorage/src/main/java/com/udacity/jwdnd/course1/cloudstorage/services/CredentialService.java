package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    @Autowired //defines getter and setter
    private CredentialMapper credentialMapper;
    @Autowired
    private EncryptionService encryptionService;

    public int createCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        credential.setKey(encodedSalt);

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(),encodedSalt);
        credential.setPassword(encryptedPassword);
        return credentialMapper.insert(credential);
    }

    public List<Credential> getAllCredentials(Integer userId){
        return credentialMapper.getUserCredentials(userId);
    }


    public int updateCredential(Credential credential){
        Credential dbCredential = credentialMapper.findByCredetialId(credential.getCredentialId());
        String key = dbCredential.getKey();
        String encryptedPassword =  encryptionService.encryptValue(credential.getPassword(), key);
        credential.setPassword(encryptedPassword);
        credential.setUserId(dbCredential.getUserId());
        credential.setKey(key);
        return credentialMapper.updateCredential(credential);
    }

//   public Credential decodePassword(Integer credentialId){
//        return credentialMapper.findByCredetialId(credentialId);
//    }

    public int deleteCredential(Integer credentialId) {
        return credentialMapper.deleteCredential(credentialId);
    }
}
