package com.example;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationAndAuthorization {

    DecodeAndVerifyPassword decodeAndVerifyPassword;
    User user;
    List<User> userList = new ArrayList<>();

    public void addUser(String name, String password) {
        decodeAndVerifyPassword = new DecodeAndVerifyPassword();
        String salt = DecodeAndVerifyPassword.generateSalt(5).get();
        password = DecodeAndVerifyPassword.hashPassword(password, salt).get();
        user = new User(name, password, salt);
        userList.add(user);
    }

    public boolean loggIn(String name, String password) {
        decodeAndVerifyPassword = new DecodeAndVerifyPassword();
        boolean isTrue = false;
        for (User value : userList) {
            if (name.equals(value.getName())) {
                String salt = value.getSalt();
                String passwordFromUserList = value.getPassword();
                if (DecodeAndVerifyPassword.verifyPassword(password, passwordFromUserList, salt)) {
                    isTrue = true;
                    break;
                }
            }
        }
        return isTrue;
    }


}
