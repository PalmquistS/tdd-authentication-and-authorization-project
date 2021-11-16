package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    void test_logg_in_success() throws WrongTokenReturnException {
        String isLoggedIn = authenticationAndAuthorization.loggIn("anna", "losen");
        assertEquals(authenticationAndAuthorization.userList.get(0).getToken(),isLoggedIn);
    }

    @Test
    void test_logg_in_fail_because_of_wrong_token_return() {
        WrongTokenReturnException wrongTokenReturnException = assertThrows(WrongTokenReturnException.class, ()->
                 authenticationAndAuthorization.loggIn("anna", "losenn"));

        assertEquals("No token found",wrongTokenReturnException.getMessage());
    }
}
