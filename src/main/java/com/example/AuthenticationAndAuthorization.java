package com.example;

public class AuthenticationAndAuthorization {

    public boolean loggIn(String name, String password) {
        return name.equals("anna") && password.equals("losen");
    }
}
