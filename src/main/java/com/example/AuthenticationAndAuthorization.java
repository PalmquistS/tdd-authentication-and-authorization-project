package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AuthenticationAndAuthorization {

    EncodeAndVerifyPassword encodeAndVerifyPassword;
    User user;
    UserRights userRights;
    List<User> userList = new ArrayList<>();
    List<UserRights> userRightsList = new ArrayList<>();

    // TODO Make a salt length random generator
    public void addUser(String name, String password, List<String> resource, List<String> rights) throws UserNameAlreadyExistsException {
        encodeAndVerifyPassword = new EncodeAndVerifyPassword();
        boolean userNameExists = false;
        for (User value : userList) {
            if (name.equals(value.getName())) {
                userNameExists = true;
            }
        }
        if (!userNameExists) {
            String salt = EncodeAndVerifyPassword.generateSalt(5).get();
            password = EncodeAndVerifyPassword.hashPassword(password, salt).get();
            String token = createToken(name, salt);
            user = new User(name, password, salt, token);
            userList.add(user);
            userRights = new UserRights(token, resource, rights);
            userRightsList.add(userRights);
        } else throw new UserNameAlreadyExistsException("Username already exists");
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

    public List<String> getUsersRightsInProgram(String token, String resourceName) throws WrongResourceNameException {
        List<String> retList = new ArrayList<>();
        for (UserRights value : userRightsList) {
            if (token.equals(value.getToken())) {
                for (int i = 0; i < value.getResource().size(); i++) {
                    if (value.getResource().get(i).equals(resourceName)) {
                        retList.add(value.getRights().get(i));
                    } else throw new WrongResourceNameException("No resource with that name found");
                }
            }
        }
        return retList;
    }
}