package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;


    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public boolean isCredentialsAvailable(Integer id) {
        return credentialsMapper.getCredentials(id) == null;
    }

    public int createCredentials(Credentials credentials) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedSalt = Base64.getEncoder().encodeToString(key);
        String hashedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedSalt);
        credentials.setKey(encodedSalt);
        return credentialsMapper.insert(new Credentials(null, credentials.getUrl(), credentials.getUsername(),
                credentials.getKey(), hashedPassword, credentials.getUserId()));
    }

    public int updateCredentials(Credentials credentials) {
        System.out.println("Credentials updated");
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedSalt = Base64.getEncoder().encodeToString(key);
        String hashedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedSalt);
        credentials.setKey(encodedSalt);

        return credentialsMapper.updateCredentials(new Credentials(credentials.getCredentialId(), credentials.getUrl(),
                credentials.getUsername(), credentials.getKey(), hashedPassword, credentials.getUserId()));
    }

    public Credentials getCredentials(Integer id) {
        return credentialsMapper.getCredentials(id);
    }

    public List<Credentials> getAllCredentials(Integer id) {
        return credentialsMapper.getAllCredentials(id);
    }

    public Credentials deleteCredentials(Integer id) {
        return credentialsMapper.deleteCredentials(id);
    }

}