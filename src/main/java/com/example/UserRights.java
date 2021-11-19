package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRights {
    private final Map<Resource, List<String>> resourcesRights = new HashMap<>();

    public UserRights(Resource resource, List<String> rights) {
        resourcesRights.put(resource, rights);
    }


    public Map<Resource, List<String>> getResourcesRights() {
        return resourcesRights;
    }
}
