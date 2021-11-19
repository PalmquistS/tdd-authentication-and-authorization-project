package com.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AuthenticationAndAuthorization {

    EncodeAndVerifyPassword encodeAndVerifyPassword;
    User user;
    UserRights userRights;
    List<User> userList = new ArrayList<>();
    Map<String, UserRights> userRightsList = new HashMap<>();

    // TODO Make a salt length random generator
    public void addUser(String name, String password) throws UserNameAlreadyExistsException {
        encodeAndVerifyPassword = new EncodeAndVerifyPassword();

        if (!userNameAlreadyExists(name)) {
            String salt = EncodeAndVerifyPassword.generateSalt(5).get();
            password = EncodeAndVerifyPassword.hashPassword(password, salt).get();
            String token = createToken(name, salt);
            user = new User(name, password, salt, token);
            userList.add(user);
        } else throw new UserNameAlreadyExistsException("Username already exists");
    }

    private boolean userNameAlreadyExists(String name) {
        return userList.stream()
                .anyMatch(p -> p.getName().equals(name));
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
        return userList.stream()
                .anyMatch(p -> p.getToken().equals(token));
    }

    public List<String> getUsersRightsInProgram(String token, String resourceName) throws WrongResourceNameException {
        List<String> retList = userRightsList.get(token).getResourcesRights().get(resourceName);
        if (retList == null) {
            throw new WrongResourceNameException("No resource with that name found");
        }
        return retList;
    }

    public void giveUserRightsInSystem(String token, String resource, List<String> rights) {
        UserRights userRights = new UserRights(resource, rights);
        userRightsList.put(token, userRights);
    }
}