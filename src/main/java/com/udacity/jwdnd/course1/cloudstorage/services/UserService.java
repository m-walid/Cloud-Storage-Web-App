package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dtos.SignupDto;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class UserService {

    private final HashService hashService;
    private final UserMapper userMapper;

    public UserService(HashService hashService, UserMapper userMapper) {
        this.hashService = hashService;
        this.userMapper = userMapper;
    }

    public Integer createUser(SignupDto signupFormDto) {
        getUser(signupFormDto.getUsername()).ifPresent(user -> {
            throw new ResponseStatusException(BAD_REQUEST, "User already exists");
        });
        String encodedSalt = hashService.getRandomSalt();
        String hashedPassword = hashService.getHashedValue(signupFormDto.getPassword(), encodedSalt);
        Integer userId = userMapper.add(new User(null, signupFormDto.getUsername(), encodedSalt, hashedPassword, signupFormDto.getFirstname(), signupFormDto.getLastname()));
        return userId;
    }

    public Optional<User> getUser(String username) {
        return userMapper.findByUsername(username);
    }

    public Optional<User> getUser(Integer id) {
        return userMapper.findById(id);
    }

}
