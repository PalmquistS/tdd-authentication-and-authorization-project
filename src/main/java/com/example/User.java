package com.example;

public class User {
    private String password;
    private String salt;
    private String token;

    public User(String password, String salt, String token) {
        this.password = password;
        this.salt = salt;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getToken() {
        return token;
    }
}
