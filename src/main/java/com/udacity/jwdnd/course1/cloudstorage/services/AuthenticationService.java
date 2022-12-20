package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService implements AuthenticationProvider {

    private final UserService userService;
    private final HashService hashService;

    public AuthenticationService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        return userService.getUser(username)
                .filter(user -> isPasswordValid(password, user))
                .map(user -> new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>()))
                .orElseThrow(() -> new AuthenticationException("Invalid username or password") {
                });
    }

    public boolean isPasswordValid(String password, User user) {
        String hashedPassword = hashService.getHashedValue(password, user.getSalt());
        return hashedPassword.equals(user.getPassword());
    }

    public User getCurrentUser() {
        return userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new AuthenticationException("Unable to get current user") {
                });
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
