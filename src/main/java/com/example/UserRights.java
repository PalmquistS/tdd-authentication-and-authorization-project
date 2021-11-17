package com.example;

import java.util.List;

public class UserRights {
    private String token;
    private List<String> resource;
    private List<String> rights;

    public UserRights(String token, List<String> resource, List<String> rights) {
        this.token = token;
        this.resource = resource;
        this.rights = rights;
    }

    public String getToken() {
        return token;
    }

    public List<String> getResource() {
        return resource;
    }

    public List<String> getRights() {
        return rights;
    }
}
