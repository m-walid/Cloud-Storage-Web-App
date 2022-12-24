package com.udacity.jwdnd.course1.cloudstorage.dtos;

public class CredentialDto {

    private Integer id;
    private String url;
    private String username;
    private String password;
    private String decryptedPassword;

    public CredentialDto(Integer id, String url, String username, String password, String decryptedPassword) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.password = password;
        this.decryptedPassword = decryptedPassword;
    }

    public CredentialDto() {
    }

    public String getDecryptedPassword() {
        return decryptedPassword;
    }

    public void setDecryptedPassword(String decryptedPassword) {
        this.decryptedPassword = decryptedPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
