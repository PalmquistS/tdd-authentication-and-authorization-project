package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AuthenticationAndAuthorization {

    EncodeAndVerifyPassword encodeAndVerifyPassword;
    User user;
    List<User> userList = new ArrayList<>();

    // TODO Make a salt length random generator
    public void addUser(String name, String password) {
        encodeAndVerifyPassword = new EncodeAndVerifyPassword();
        String salt = EncodeAndVerifyPassword.generateSalt(5).get();
        password = EncodeAndVerifyPassword.hashPassword(password, salt).get();
        String token = createToken(name, salt);
        user = new User(name, password, salt, token);
        userList.add(user);
    }

    private String createToken(String name, String salt) {
        return shuffleWord(name + salt);
    }

    public static String shuffleWord(String text) {
        List<Character> characters = text.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        IntStream.range(0, text.length()).forEach((index) -> {
            int randomPosition = new Random().nextInt(characters.size());
            result.append(characters.get(randomPosition));
            characters.remove(randomPosition);
        });
        return result.toString();
    }

    public String loggIn(String name, String password) throws WrongTokenReturnException {
        encodeAndVerifyPassword = new EncodeAndVerifyPassword();
        boolean isTrue = false;
        String tokenToBeReturned = "";
        for (User value : userList) {
            if (name.equals(value.getName())) {
                String salt = value.getSalt();
                String passwordFromUserList = value.getPassword();
                if (EncodeAndVerifyPassword.verifyPassword(password, passwordFromUserList, salt)) {
                    tokenToBeReturned = value.getToken();
                    break;
                }
            }
        }
        if (tokenToBeReturned.equals("")) {
            throw new WrongTokenReturnException("No token found");
        }
        return tokenToBeReturned;
    }


    public boolean validateToken(String token) {
        for (User value : userList) {
            if (token.equals(value.getToken())) {
                return true;
            }
        }
        return false;
    }
}
