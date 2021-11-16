package com.example;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationAndAuthorization {

    EncodeAndVerifyPassword encodeAndVerifyPassword;
    User user;
    List<User> userList = new ArrayList<>();

    public void addUser(String name, String password) {
        encodeAndVerifyPassword = new EncodeAndVerifyPassword();
        String salt = EncodeAndVerifyPassword.generateSalt(5).get();
        password = EncodeAndVerifyPassword.hashPassword(password, salt).get();
        user = new User(name, password, salt);
        userList.add(user);
    }

    public boolean loggIn(String name, String password) {
        encodeAndVerifyPassword = new EncodeAndVerifyPassword();
        boolean isTrue = false;
        for (User value : userList) {
            if (name.equals(value.getName())) {
                String salt = value.getSalt();
                String passwordFromUserList = value.getPassword();
                if (EncodeAndVerifyPassword.verifyPassword(password, passwordFromUserList, salt)) {
                    isTrue = true;
                    break;
                }
            }
        }
        return isTrue;
    }


}
