package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationAndAuthorizationTest {

    @Test
    void test_logg_in_success() {
        AuthenticationAndAuthorization authenticationAndAuthorization = new AuthenticationAndAuthorization();
        boolean isLoggedIn = authenticationAndAuthorization.loggIn("anna", "losen");
        assertTrue(isLoggedIn);

    }
}
