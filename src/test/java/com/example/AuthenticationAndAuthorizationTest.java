package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationAndAuthorizationTest {

    AuthenticationAndAuthorization authenticationAndAuthorization;

    @BeforeEach
    void setUp() {
        authenticationAndAuthorization = new AuthenticationAndAuthorization();
        authenticationAndAuthorization.addUser("anna", "losen", List.of("ACCOUNT"), List.of("READ"));
        authenticationAndAuthorization.addUser("bertil", "123456", List.of("ACCOUNT","ACCOUNT"), List.of("READ", "WRITE"));
        authenticationAndAuthorization.addUser("kalle", "password", List.of("PROVISION_CALC"), List.of("EXECUTE"));
    }

    @Test
    void test_logg_in_success() throws WrongTokenReturnException {
        String tokenInReturn = authenticationAndAuthorization.loggIn("anna", "losen");

        assertEquals(authenticationAndAuthorization.userList.get(0).getToken(), tokenInReturn);
    }

    @Test
    void test_logg_in_fail_because_of_wrong_token_return() {
        WrongTokenReturnException wrongTokenReturnException = assertThrows(WrongTokenReturnException.class, () ->
                authenticationAndAuthorization.loggIn("anna", "losenn"));

        assertEquals("No token found", wrongTokenReturnException.getMessage());
    }

    @Test
    void test_check_if_token_is_valid_success() throws WrongTokenReturnException {
        String tokenInReturn = authenticationAndAuthorization.loggIn("anna", "losen");
        boolean isTokenValid = authenticationAndAuthorization.validateToken(tokenInReturn);

        assertTrue(isTokenValid);
    }

    @Test
    void test_check_users_rights_in_program_success() throws WrongTokenReturnException, NoRecourceNameException {
        String tokenInReturn =   authenticationAndAuthorization.loggIn("bertil", "123456");
        List<String> userRights = authenticationAndAuthorization.getUsersRightsInProgram(tokenInReturn, "ACCOUNT");

        assertEquals(List.of("READ","WRITE"), userRights);
    }
    @Test
    void test_check_users_rights_fail_because_of_wrong_resource_name() throws WrongTokenReturnException {
        String tokenInReturn =   authenticationAndAuthorization.loggIn("bertil", "123456");
        NoRecourceNameException noRecourceNameException = assertThrows(NoRecourceNameException.class, () ->
                authenticationAndAuthorization.getUsersRightsInProgram(tokenInReturn, "WrongResourceName"));

        assertEquals("No resource with that name found", noRecourceNameException.getMessage());
    }

}
