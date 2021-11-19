package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRights {
    private final Map<String, List<String>> resourcesRights = new HashMap<>();

    public UserRights(String resource, List<String> rights) {
        resourcesRights.put(resource, rights);
    }


    public Map<String, List<String>> getResourcesRights() {
        return resourcesRights;
    }
}
