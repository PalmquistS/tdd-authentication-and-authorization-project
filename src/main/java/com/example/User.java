package com.example;

public class User {
    private String name;
    private String password;
    private String salt;

    public User(String name, String password, String salt) {

        this.name = name;
        this.password = password;
        this.salt = salt;
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
}
