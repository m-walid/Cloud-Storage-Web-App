package com.udacity.jwdnd.course1.cloudstorage.dtos;

public class SignupDto {
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public SignupDto(String username, String password, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public SignupDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void clear() {
        this.username = null;
        this.password = null;
        this.firstname = null;
        this.lastname = null;
    }
}
