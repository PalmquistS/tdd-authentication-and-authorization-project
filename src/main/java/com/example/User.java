package com.example;

public class User {
    private String name;
    private String password;
    private String salt;
    private String token;

    public User(String name, String password, String salt, String token) {

        this.name = name;
        this.password = password;
        this.salt = salt;
        this.token = token;
    }

    public String getName() {
        return name;
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
