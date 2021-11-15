package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthenticationAndAuthorizationTest {
    AuthenticationAndAuthorization authenticationAndAuthorization;
    @BeforeEach
    void setUp() {
        authenticationAndAuthorization = new AuthenticationAndAuthorization();
        authenticationAndAuthorization.addUser("anna", "losen");
        authenticationAndAuthorization.addUser("bertil", "123456");
        authenticationAndAuthorization.addUser("kalle", "password");
    }
    @Test
    void test_logg_in_success() {
        boolean isLoggedIn = authenticationAndAuthorization.loggIn("anna", "losen");
        assertTrue(isLoggedIn);

    }
}