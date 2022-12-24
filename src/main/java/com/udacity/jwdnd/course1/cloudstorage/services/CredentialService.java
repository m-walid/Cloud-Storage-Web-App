package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dtos.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    private final AuthenticationService authenticationService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService, AuthenticationService authenticationService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
        this.authenticationService = authenticationService;
    }

    public Integer add(CredentialDto credentialDto) {

        String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credentialDto.getPassword(), key);
        Credential credential = new Credential(
                credentialDto.getUrl(),
                credentialDto.getUsername(),
                key,
                encryptedPassword,
                authenticationService.getCurrentUser().getId()
        );
        return this.credentialMapper.add(credential);
    }

    public void update(CredentialDto credentialDto) {
        Credential credential = this.credentialMapper.findById(credentialDto.getId()).orElseThrow(() -> new RuntimeException("Credential not found"));
        User currentUser = authenticationService.getCurrentUser();
        if (!credential.getUserId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to update this credential");
        }
        String encryptedPassword = encryptionService.encryptValue(credentialDto.getPassword(), credential.getKey());
        credential.setUrl(credentialDto.getUrl());
        credential.setUsername(credentialDto.getUsername());
        credential.setPassword(encryptedPassword);
        this.credentialMapper.update(credential);
    }

    public void addOrUpdate(CredentialDto credentialDto) {
        if (credentialDto.getId() == null) {
            this.add(credentialDto);
        } else {
            this.update(credentialDto);
        }
    }

    public void delete(Integer credentialId) {
        Credential credential = this.credentialMapper.findById(credentialId).orElseThrow(() -> new RuntimeException("Credential not found"));
        User currentUser = authenticationService.getCurrentUser();
        if (!credential.getUserId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this credential");
        }
        this.credentialMapper.delete(credentialId);

    }

    public CredentialDto[] getUserCredentials() {
        User currentUser = authenticationService.getCurrentUser();
        return Arrays.stream(this.credentialMapper.findByUserId(currentUser.getId()))
                .map(credential -> new CredentialDto(
                        credential.getId(),
                        credential.getUrl(),
                        credential.getUsername(),
                        credential.getPassword(),
                        this.encryptionService.decryptValue(credential.getPassword(), credential.getKey())
                )).toArray(CredentialDto[]::new);
    }
}
