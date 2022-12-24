package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileAlreadyExistsException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileIsEmptyException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.UnAuthorizedUserException;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final AuthenticationService authenticationService;

    public FileService(FileMapper fileMapper, AuthenticationService authenticationService) {
        this.fileMapper = fileMapper;
        this.authenticationService = authenticationService;
    }

    public Integer saveFile(MultipartFile fileUpload){
        User currentUser = authenticationService.getCurrentUser();
        if(fileUpload.isEmpty()) {
            throw new FileIsEmptyException();
        }
        String fileName = StringUtils.cleanPath(fileUpload.getOriginalFilename());

        if(isFileAvailable(fileName, currentUser.getId())) {
            throw new FileAlreadyExistsException();
        }
        try {
            File file = new File(
                    fileName,
                    fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()),
                    currentUser.getId(),
                    fileUpload.getBytes());
            return this.fileMapper.add(file);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file", e);
        }

    }

    public File[] getUserFiles() {
        User currentUser = authenticationService.getCurrentUser();
        return this.fileMapper.findByUserId(currentUser.getId());
    }

    public void deleteFile(Integer fileId) {
        File file = this.fileMapper.findById(fileId).orElseThrow((FileNotFoundException::new));
        User currentUser = authenticationService.getCurrentUser();
        if(file.getUserId().equals(currentUser.getId())) {
            this.fileMapper.delete(fileId);
        }
        else {
            throw new UnAuthorizedUserException();
        }
    }

    public File getFile(Integer fileId) {
        File file = this.fileMapper.findById(fileId).orElseThrow((FileNotFoundException::new));
        User currentUser = authenticationService.getCurrentUser();
        if(file.getUserId().equals(currentUser.getId())) {
            return file;
        }
        else {
            throw new UnAuthorizedUserException();
        }
    }


    private boolean isFileAvailable(String fileName, Integer userId) {
        return this.fileMapper.getFileByNameAndUserId(fileName, userId).isPresent();
    }

}
