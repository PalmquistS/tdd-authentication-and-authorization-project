package com.example;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AuthenticationAndAuthorization {

    User user;
    UserRights userRights;
    Map<String, User> userList = new HashMap<>();
    Map<String, UserRights> userRightsList = new HashMap<>();

    // TODO Make a salt length random generator
    public void addUser(String name, String password) throws UserNameAlreadyExistsException {
        if (!userList.containsKey(name)) {
            String salt = EncodeAndVerifyPassword.generateSalt(5).get();
            password = EncodeAndVerifyPassword.hashPassword(password, salt).get();
            String token = createToken(name, salt);
            user = new User(password, salt, token);
            userList.put(name, user);
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
        if (EncodeAndVerifyPassword.verifyPassword(password,
                userList.get(name).getPassword(),
                userList.get(name).getSalt())) {
            return userList.get(name).getToken();
        } else throw new WrongTokenReturnException("No token found");
    }


    public boolean validateToken(String token) {
        return userList.entrySet().stream().anyMatch(p -> p.getValue().getToken().equals(token));
    }

    public List<String> getUsersRightsInProgram(String token, Resource resourceName)  {
        List<String> retList = userRightsList.get(token).getResourcesRights().get(resourceName);
        return retList;
    }

    public void giveUserRightsInSystem(String token, Resource resource, List<String> rights) {
        UserRights userRights = new UserRights(resource, rights);
        userRightsList.put(token, userRights);
    }
}