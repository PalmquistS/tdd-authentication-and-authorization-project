package com.example;

import java.util.ArrayList;
import java.util.List;

public class AuthenticationAndAuthorization {
    User user;
    List<User> userList = new ArrayList<>();

    public void addUser(String name, String password) {
        user = new User(name, password);
        userList.add(user);
    }

    public boolean loggIn(String name, String password) {
        boolean isTrue = false;
        for (User value : userList) {
            if (name.equals(value.getName())) {
                if (password.equals(value.getPassword())) {
                    isTrue = true;
                    break;
                }
            }
        }
        return isTrue;
    }
}
