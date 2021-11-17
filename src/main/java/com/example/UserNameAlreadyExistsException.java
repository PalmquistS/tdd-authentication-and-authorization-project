package com.example;

public class UserNameAlreadyExistsException extends Exception {
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
